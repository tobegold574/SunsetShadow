<template>
  	<view style="display:flex">
    <image src="@/static/b716d1a5cb23bbc63997704e1e5dbd13.jpg" class="bg-img"></image>
  	</view>
  <view>
    <view style="display:flex;justify-content:center;align-items:center;margin-top:4vh">
      <text style="font-size: 1.5em;color: #000;">好友列表</text>
    </view>
    <view class="horizontal-divider"></view>
    <ul>
      <li v-for="friend in friends" :key="friend.id" @click="navigateToConversation(friend)">
        <text>{{ friend.username }}</text>
      </li>
    </ul>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { HttpManager } from '@/api';
import type { ResponseBody } from '@/types/interface';
import { useStatus } from '@/store/status';
import type { friend } from '@/types/friend';
import {onLoad} from '@dcloudio/uni-app'; 

const status = useStatus();
const friends = ref<friend[]>([]);

const fetchFriends = async () => {
  try {
    const response: ResponseBody = await HttpManager.getFriends(status.id) as ResponseBody;
    if (response.success) {
      friends.value = response.data as friend[];
    } else {
      console.error('Failed to fetch friends');
    }
  } catch (error) {
    console.error('Error fetching friends', error);
  }
};

const navigateToConversation = (friend: any) => {
  uni.navigateTo({
    url: `/pages/community/conversations?friendId=${friend.id}&friendUsername=${friend.username}`
  });
};

onLoad(fetchFriends);
</script>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

li {
  cursor: pointer;
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

li:hover {
  background-color: #f0f0f0;
}
</style>