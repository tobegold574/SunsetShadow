<template>
  <view style="display:flex">
    <image src="@/static/049df46a1e123a6ca087d1e0fcbde77c.jpg" class="bg-img"></image>
  
  </view>
  <view style="margin-top:5vh">
    <view style="margin-top: 2vh;" v-for="message in messages" :key="message.id">
      <view style="margin-top:2vh">
        <text style="margin-left:60vw" v-if="message.sender === username">我:{{ message.content }}</text>
      </view>
      <view style="margin-top:2vh">
        <text style="margin-left:5vw;" v-if="message.sender !== username">{{ message.sender }}:{{ message.content }}</text>
      </view>
    </view>
  </view> 
  <view style="display:flex;justify-content:center;align-items:center;margin-top:60vh;width:100%">
        <input v-model="newMessage" placeholder="输入你想说的话..." color="#333" style="width:80%;margin-top:2vh;margin-bottom:2vh;position:fixed"/>
        <uni-icons type="chatbubble" style="position:fixed;margin-left:50vw"size="30" color="white"  @click="sendMessage"></uni-icons>
      </view>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useStatus } from '@/store/status';
import type { ChatMessage } from '@/types/chatMessage';
import { onLoad } from '@dcloudio/uni-app';
import { StompClient } from '@/types/stomp';
const status = useStatus();
const username = status.username;

const messages = ref<ChatMessage[]>([]);
const newMessage = ref<string>('');


const recipientId = ref<number | null>(null);
const recipientUsername = ref<string | null>(null);
onLoad((options: any) => {
  recipientId.value = options.friendId ? Number(options.friendId) : null;
  recipientUsername.value = options.friendUsername || null;
  initWS();

  StompClient.subscribe('/topic/public', (message)=>{
    console.log('public message received');
    if(JSON.parse(message.body).type === 'JOIN'){
      uni.showToast({
        title: '用户' + JSON.parse(message.body).sender + '加入聊天',
        icon: 'none',
        duration: 3000,
      });
    }
    console.log('messages:', message.body);
  });

  StompClient.subscribe('/user/queue/messages', (message)=>{
    console.log('private message received');
    messages.value.push(JSON.parse(message.body));
    console.log('message:', JSON.parse(message.body));
  });

  StompClient.send('/app/chat.addUser', {}, JSON.stringify({
  sender: username,
    type: 'JOIN'
  }));

});

const initWS = () => {
  StompClient.connect(
    `ws://localhost:8080/chat?username=${username}`,
    {},
    (frame)=>{console.log('stompClient:', frame)},
    (error)=>{console.log('stompClient连接失败', error)}
  );
  
};



const sendMessage = () => {
  if (newMessage.value.trim() !== '' && recipientUsername.value) {
    console.log('newMessage:', newMessage.value);
    const chatMessage: ChatMessage = {
      sender: username,
      recipient: recipientUsername.value,
      content: newMessage.value,
      type: 'CHAT',
    };
    messages.value.push(chatMessage);
    StompClient.send('/app/chat.sendPrivateMessage', {}, JSON.stringify(chatMessage));
    newMessage.value = '';
  }
};


</script>

<style>
.send-button{
  margin-top:2vh;
  margin-bottom:2vh;
  opacity:0.95;
  box-shadow: 0 0 10px 0 rgba(219, 13, 150, 0.5);
  border-radius: 10%;
}
</style>