<template>
    <view style="display:flex">
    <image src="@/static/5b7ec69a8fc976a6e04b576c7d19de20.jpg" class="bg-img"></image>
  </view>
  <view style="display:flex;justify-content:center;align-items:center;margin-top:4vh">
    <text style="font-size: 1.5em;color: #000;">好友请求列表</text>
  </view>
  <view class="horizontal-divider"></view>
  <view>
    <ul>
    <li v-for="friendRequest in friendRequests" :key="friendRequest.id">
    <view style="display:flex;justify-content:center;align-items:center;margin-top:4vh">
      <text>{{ friendRequest.username }}</text>
      <button @click="acceptFriendRequest(friendRequest.id)">接受</button>
      <text>/</text>
      <button @click="rejectFriendRequest(friendRequest.id)">拒绝</button>
    </view>
    <view class="horizontal-divider"></view>
    </li>
  </ul>
  </view>
</template>


<script setup lang='ts'>
import { HttpManager } from '@/api';
import { useStatus } from '@/store/status';
import { ref } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
import type { ResponseBody } from '@/types/interface';
import type{friend} from '@/types/friend';
const status = useStatus();
const friendRequests = ref<friend[]>([]);
const receiver_id=status.id;
const acceptFriendRequest = async (id: number) => {
    return HttpManager.updateApplication({ sender_id: id, receiver_id: status.id, accept: true });
  };

  const rejectFriendRequest = async (id: number) => {
    return HttpManager.updateApplication({ sender_id: id, receiver_id: status.id, accept: false });
  };

  const fetchFriendRequests = async () => {
    try {
      const response: ResponseBody = (await HttpManager.getApplicationList(receiver_id)) as ResponseBody;
      if (response.success) {
        friendRequests.value = response.data;
      }
    } catch (error) {
      console.log('Error fetching friend requests');
    }
  };

  onLoad(() => {
    fetchFriendRequests();
  });
</script>

<style scoped>
button{
    opacity: 0.8;
    border-radius: 10%;
    border: 1px solid #37b4f3;
    color: #37b4f3;
    box-shadow: 0 0 10px #37b4f3;
    margin-left: 10vw;
    margin-right: 10vw;
}
</style>