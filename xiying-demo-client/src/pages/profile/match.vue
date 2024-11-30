<template>
  <view style="display:flex">
    <image src="@/static/IMG_20210514_182856.jpg" class="bg-img"></image>
  </view>
  <view style="display:flex;justify-content:center;align-items:center;margin-top:40vh">
       <radio-group @change="(event)=>{sex=event.detail.value}">
        <label>
          <radio value="男" />
          男
        </label>
        <label>
          <radio value="女" />
          女
        </label>
       </radio-group>
  </view>
  <view style="display:flex;justify-content:center;align-items:center;margin-top:4vh">
    <label style="font-size: 1.5em;color: #000;"></label>
    <uni-icons type="location" size="50" margin-right=15vw></uni-icons>
      <input v-model="location" placeholder="选择你感兴趣的城市">
  </view>
  <button class="match-button" @click="submitMatch">寻找</button>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { HttpManager } from '@/api';
import { useStatus } from '@/store/status';
import type { ResponseBody } from '@/types/interface';

const status = useStatus();
const sex=ref<string>('');
const location = ref<string>('');
const bestId = ref<number>(0);
const bestScore = ref<number>(0);

const submitMatch = async () => {
  try {
    const response = await HttpManager.match({ user_id: status.id, sex: sex.value, location: location.value }) as ResponseBody;
    console.log('Match response:', response);
    if(response.data.bestId!==0){
      bestId.value = response.data.bestId;
      bestScore.value = response.data.bestScore;
      uni.showToast({
        title: '匹配成功\n匹配度：'+`${bestScore.value*100}%`,
        icon: 'success',
        duration: 2000
      });
      uni.navigateTo({
        url: `/pages/profile/public?id=${bestId.value}`,
      });
    }

  } catch (error) {
    console.error('Error finding match:', error);
  }
};
</script>

<style scoped>

.match-button{
  border: 1px solid #3d18c4;
  box-shadow: 0 0 5vw #3d18c4;
  border-radius: 10%;
  padding: 5px;
  margin:15vh
}

</style>

