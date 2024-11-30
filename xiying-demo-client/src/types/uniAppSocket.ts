import { Stomp, type Client, type Frame } from "@stomp/stompjs";

export class UniAppSocket{
    private static client: Client|null = null;

    static init(url:string, header:any, connectWS:()=>void):Promise<Client>{
        if(this.client && this.client.connected){
            console.log('返回已连接的websocket连接');
            return Promise.resolve(this.client);
        }

        if(this.client){
            console.log('疑似之前的连接实例stomp层面关闭，但仍尝试自动重连');
        }

        return new Promise((resolve, reject)=>{
            if(this.client&&!this.client.connected){
                console.log('关闭旧的且未成功的websocket连接');
                uni.closeSocket();
                this.client=null;
            }


            const ws={
                send:this.send,
                onopen:()=>{},
                onmessage:(res:any)=>{},
            }

            const client = (this.client = Stomp.over(ws));
            console.log('stomp将ws包装成client', client);

            uni.connectSocket({
                url:url,
                header:header,
                success:(res)=>{
                    console.log('websocket连接成功', res);
                },
            })

            uni.onSocketOpen(()=>{
                console.log('websocket连接打开')
                if(ws.onopen){
                    console.log('执行onopen回调');
                    ws.onopen();
                }
            })

            uni.onSocketMessage((res)=>{
                ws.onmessage(res);
            })

            uni.onSocketClose((res)=>{
                this.client=null
                console.log('websocket连接关闭', res);
                if(res.code!==1000){
                    setTimeout(()=>{
                        connectWS();
                    }, 2000)
                }
            })

            uni.onSocketError((res)=>{
                console.log('websocket连接错误：', res);
            })
            
            console.log('stomp开始连接');
            client.connect(header, (frame: Frame) => {
                console.log('stomp连接成功', frame);
                resolve(client);
            }, (frame: Frame) => {
                console.log('stomp连接失败', frame);
                reject(frame);
            });
        })
    }

    static send(message:string){
        
        uni.sendSocketMessage({data:message});
    }
}
