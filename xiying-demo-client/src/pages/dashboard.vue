<template>
  <view style="display:flex">
    <image src="@\static\981ccb6bccbfaedcd9174a1e094686c7.jpg" class="bg-img"></image>
  </view>
  <view class="dashboard-container">
    <button class="community-button" @click="navigateTo('/pages/community/friendship')">好友</button>
    <button class="take-photo-button" @click="takePhoto">时刻</button>
    <button class="flow-button" @click="navigateTo('/pages/exhibition/flow')">共赏</button>
  </view>
  <view class="private-container">
    <button class="private-button" @click="navigateTo('/pages/profile/private')">我的</button>
    <button class="map-button" @click="navigateTo('/pages/map')">夕时</button>
  </view>
</template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  import { useStatus } from '@/store/status'
  import { onLoad } from '@dcloudio/uni-app';
  
  
  const status = useStatus()
  const imagePath = ref<string>('')
  
  const takePhoto = () => {
  uni.chooseImage({
    count: 1,
    sourceType: ['camera'],
    success: (res) => {
      imagePath.value = res.tempFilePaths[0];
      uni.navigateTo({
        url: `/pages/exhibition/editor?imagePath=${encodeURIComponent(imagePath.value)}`
      });
    },
    fail: (err) => {
      console.error('Failed to take photo:', err);
      uni.showToast({
        title: 'Failed to take photo',
        icon: 'none'
      });
    }
  });
}
  
  

  const navigateTo = (page: string) => {
    uni.navigateTo({
      url: `${page}`
    });
  };

  onLoad(() => {
    if (status.id===-1) {
      uni.navigateTo({
        url: "/pages/login/sign-in"
      })
    }
  })

  </script>
  
  <style>
  .dashboard-container{
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 25vh;
    margin-bottom: 1vh;
  }
  .take-photo-button{
    display: flex;
    width: 40vw;
    height:30vh;
    border-radius: 10%;
    background-color: rgb(235, 235, 235);
    border: 1px solid rgb(200, 200, 200);
    justify-content: center;
    align-items: center;
    opacity: 0.8;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  }
  .community-button{ 
    display: flex;
    width: 27vw;
    height:30vh;
    border-radius: 10%;
    background-color: rgb(235, 235, 235);
    border: 1px solid rgb(200, 200, 200);
    margin-left: 2vw;
    margin-right: 1vw;
    justify-content: center;
    align-items: center;
    opacity: 0.8;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  }
  .flow-button{
    display: flex;
    width: 27vw;
    height:30vh;
    margin-right:2vw;
    margin-left: 1vw;
    border-radius: 10%;
    background-color: rgb(235, 235, 235);
    border: 1px solid rgb(200, 200, 200);
    justify-content: center;
    align-items: center;
    opacity: 0.8;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  }
  .private-button{
    display: flex;
    width: 42vw;
    height:10vh;
    margin-right:4vw;
    margin-top: 20vh;
    margin-left: 6vw;
    border-radius: 10%;
    background-color: rgb(235, 235, 235);
    border: 1px solid rgb(200, 200, 200);
    justify-content: center;
    align-items: center;
    opacity: 0.8;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  }
  .private-container{
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 5vh;
  }
  .map-button{
    display: flex;
    margin-left: 4vw;
    margin-right: 6vw;
    margin-top: 20vh;
    width:40vw;
    height:10vh;
    border-radius: 10%;
    background-color: rgb(235, 235, 235);
    border: 1px solid rgb(200, 200, 200);
    justify-content: center;
    align-items: center;
    opacity: 0.8;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  }


  </style>