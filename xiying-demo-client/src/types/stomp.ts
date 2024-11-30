// utils/stomp.ts

interface Headers {
  [key: string]: string;
}

interface Frame {
  command: string;
  headers: Headers;
  body: string;
}

type Callback = (frame: Frame) => void;

export class StompClient {
  private static url: string;
  private static socket: UniApp.SocketTask | null = null;
  private static connected: boolean = false;
  private static heartbeat = {
    outgoing: 10000, // 每10秒发送一次心跳
    incoming: 10000  // 每10秒接收一次心跳
  };
  private static subscriptions: string[] = [];
  private static reconnectAttempts: number = 0;
  private static maxReconnectAttempts: number = 5;
  private static reconnectDelay: number = 5000; // 重连间隔时间（毫秒）

 

  // 连接到STOMP服务器
  static connect(url:string, headers: Headers = {}, connectCallback: Callback, errorCallback: (error: any) => void): Promise<void> {

    if(StompClient.socket&&StompClient.connected){
        console.log('Stomp已连接，无需重新连接');
        return Promise.resolve();
    }

    if(StompClient.socket&&!StompClient.connected){
      console.log('WebSocket已连接，但Stomp未连接成功，尝试重新连接');
      StompClient.disconnect();
    }

    return new Promise(async (resolve, reject) => {
      this.socket = await uni.connectSocket({
        url: url,
        success:()=>{
          console.log('WebSocket连接成功');
        }
      });

      this.socket?.onOpen(() => {
        this.connected = true;
        this.reconnectAttempts = 0; // 重置重连尝试次数
        this._sendCommand('CONNECT', {...headers,'accept-version':'1.2,1.1,1.0','heart-beat':'10000,10000'});
      });

      this.socket?.onMessage((res) => {
        const frame = StompClient._parseFrame(res.data);
        if (frame.command === 'CONNECTED') {
          console.log('Stomp连接成功');
          connectCallback(frame);
        } else if (frame.command === 'MESSAGE') {
          const subscription = StompClient.subscriptions[frame.headers.subscription];
          console.log('收到消息', frame);
          if (subscription) {
                subscription(frame);
            }
          }
        });

      this.socket?.onClose(() => {
        this.connected = false;
        console.log('WebSocket已断开');
        this._attemptReconnect(headers, connectCallback, errorCallback);
      });
      this.socket?.onError((error) => {
        errorCallback(error);
        this._attemptReconnect(headers, connectCallback, errorCallback);
        });
    });
  }

  // 断开连接
  static disconnect():void{
    StompClient._sendCommand('DISCONNECT');
    uni.closeSocket();
    StompClient.connected = false;
    StompClient.socket = null;
  }

  // 发送消息
  static send(destination: string, headers: Headers = {}, body: string = ''): void {
    headers.destination = destination;
    this._sendCommand('SEND', headers, body);
  }

  // 订阅目的地
  static subscribe(destination: string, callback: Callback, headers: Headers = {}): string {
    const id = `sub-${Object.keys(StompClient.subscriptions).length + 1}`;
    headers.destination = destination;
    headers.id = id;
    StompClient.subscriptions[id] = callback;
    this._sendCommand('SUBSCRIBE', headers);
    return id;
  }

  // 取消订阅
  static unsubscribe(id: string): void {
    if (this.subscriptions[id]) {
      this._sendCommand('UNSUBSCRIBE', { id });
      delete this.subscriptions[id];
    }
  }

  // 发送STOMP命令
  static _sendCommand(command: string, headers: Headers = {}, body: string = ''): void {
    const frame = this._buildFrame(command, headers, body);
    StompClient.socket?.send({ data: frame });
  }

  private static _buildFrame(command: string, headers: Headers, body: string): string {
    const headerString = Object.entries(headers)
      .map(([key, value]) => `${key}:${value}`)
      .join('\n');
    
    return `${command}\n${headerString}\n\n${body}\0`;
  }

  // 解析STOMP帧
  static _parseFrame(data: string): Frame {
    const divider = data.search(/\n\n/);
    const headerLines = data.substring(0, divider).split('\n');
    const command = headerLines.shift() || '';
    const headers: Headers = {};
    headerLines.forEach((line) => {
      const idx = line.indexOf(':');
      headers[line.substring(0, idx)] = line.substring(idx + 1);
    });
    const body = data.substring(divider + 2, data.length - 1);
    return { command, headers, body };
  }

  // 尝试重连
  static _attemptReconnect(headers: Headers, connectCallback: Callback, errorCallback: (error: any) => void): void {
    if (this.reconnectAttempts < this.maxReconnectAttempts) {
      this.reconnectAttempts++;
      setTimeout(() => {
        console.log(`正在尝试第${this.reconnectAttempts}次重连...`);
        StompClient.connect(StompClient.url, headers, connectCallback, errorCallback);
      }, this.reconnectDelay);
    } else {
      console.log('已达到最大重连次数');
    }
  }
}

