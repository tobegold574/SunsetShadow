<template>
   <view style="display:flex">
    <image src="@/static/5f02a440ca25c33aabc5f493c97ce2f8.jpg" class="bg-img"></image>
  </view>
    <view style="margin-top:20vh">
      <view style="display:flex;justify-content:center;align-items:center;">
      <image :src="avatarUrl" alt="null" v-if="avatarUrl" class="avatar"/>
      </view>
      <view class="space">
        <uni-icons type="contact" size="50" margin-right=15vw></uni-icons>
        <text style="font-size: 3em; ">{{ profile.username }}</text>
      </view>
      <view class="horizontal-divider"></view>
      <view class="space">
        <uni-icons type="circle-filled" size="50" margin-right=15vw></uni-icons>
        <text>{{ profile.sex }}</text>
      </view>
      <view class="horizontal-divider"></view>
      <view class="space">
        <uni-icons type="location" size="50" margin-right=15vw></uni-icons>
        <text>{{ profile.location }}</text>
      </view>
      <view class="horizontal-divider"></view>
      <view class="space">
        <uni-icons type="calendar" size="50" margin-right=15vw></uni-icons>
        <text>{{ profile.birthday }}</text>
      </view>
      <view class="horizontal-divider"></view>
      <view class="space">
        <uni-icons type="info-filled" size="50" margin-right=15vw></uni-icons>
        <text>{{ profile.introduction }}</text>
      </view>
      <view class="horizontal-divider"></view>
      <button @click="sendFriendRequest" class="send-button">发送好友请求</button>
    </view>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import { useStatus } from '@/store/status';
import { HttpManager } from '@/api';
import type { ResponseBody } from '@/types/interface';
import { onLoad } from '@dcloudio/uni-app';

const status = useStatus();
const userId = ref<number>(0);
const profile = reactive({
  sex: '',
  username: '',
  location: '',
  introduction: '',
  birthday: '',
  avatar: '', // Filename of the avatar
});
const avatarUrl = ref('');

onLoad((options: any) => {
  userId.value = Number(decodeURIComponent(options.id as string));
  fetchProfile(userId.value);
});

const fetchProfile = async (userId: number) => {
  try {
    const response: ResponseBody = (await HttpManager.getInfo(userId)) as ResponseBody;
    if (response.success) {
      Object.assign(profile, response.data);
      if(profile.avatar){
        await fetchAvatar(Number(userId)); // Fetch the avatar after fetching the profile info
      }
    } else {
      console.log('Failed to fetch profile');
    }
  } catch (error) {
    console.log('Error fetching profile');
  }
};

const fetchAvatar = async (userId: number) => {
  try {
    const bytearray = (await HttpManager.getAvatar({ user_id: userId })).data as ArrayBuffer;
    console.log('获取头像');
        avatarUrl.value = `data:image/png;base64,${uni.arrayBufferToBase64(bytearray)}`;
  } catch (error) {
    console.log('Error fetching avatar');
  }
};

const sendFriendRequest = async () => {
  try {
    console.log(status.id,userId.value);
    const response: ResponseBody = (await HttpManager.sendApplication({ sender_id: status.id, receiver_id: userId.value})) as ResponseBody;
    if (response.success) {
      console.log('Friend request sent successfully');
    } else {
      console.log('Failed to send friend request');
    }
  } catch (error) {
    console.log('Error sending friend request');
  }
};

</script>

<style scoped>
.send-button{
  margin-top: 10vh;
  margin-right:32vw;
  margin-left:32vw;
  opacity:0.8;
  border-radius: 10%;
  border: 1px solid #3fbe54;
  box-shadow: 0 0 10px #3fbe54;
}
</style>