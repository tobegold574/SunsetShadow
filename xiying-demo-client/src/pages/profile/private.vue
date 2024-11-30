<template>
  <view style="display:flex">
    <image src="@/static/5f02a440ca25c33aabc5f493c97ce2f8.jpg" class="bg-img"></image>
  </view>
  <view>
    <view v-if="!showUpdateForm" style="margin-top:3vh;margin-bottom:2vh;">
      <view style="display:flex;width:100%">
      <uni-icons type="plusempty" size="20" margin-right=10vw></uni-icons>
      <input v-model="searchUserId" placeholder="Enter User ID" @keyup.enter="searchUser">
      <button @click="searchUser" class="search-button">搜索</button>
    </view>
      <view v-if="!showUpdateForm" class="horizontal-divider"></view>
    </view>
    <view v-if="!showUpdateForm">
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
    </view>
    <view style="display:flex;justify-content:center;align-items:center;margin-top:10vh">
      <button v-if="!showUpdateForm" class="update" @click="showUpdateForm = !showUpdateForm">
      更新档案
      </button>
      <button v-if="!showUpdateForm" class="application-list" @click="navigateTo('/pages/profile/applicationlist')">请求列表</button>
    </view>
    <view style="display:flex;justify-content:center;align-items:center;margin-top:4vh">
      <button v-if="!showUpdateForm" class="match" @click="navigateTo('/pages/profile/match')">匹配</button>
    </view>
    <view style="display:flex;justify-content:center;align-items:center;margin-top:4vh" v-if="showUpdateForm">
      <form>
        <view style="display:flex;">
          <label for="username" style="font-size: 1em;color: #000;">用户名:</label>
          <input v-model="profile.username" id="username" type="text" />
        </view>
        <view class="horizontal-divider"></view>
        <view>
          <radio-group @change="(event)=>{profile.sex=event.detail.value}">
          <label>
            <radio value="男" />
            男
          </label>
          <label>
            <radio value="女"  />
            女
          </label>
        </radio-group>
          <view class="horizontal-divider"></view>
        </view>
        <view style="display:flex;">
          <label for="location" style="font-size: 1em;color: #000;">城市:</label>
          <input v-model="profile.location" id="location" type="text" />
        </view>
        <view class="horizontal-divider"></view>
        <view style="display: flex;">
          <label for="birthday" style="font-size: 1em;color: #000;">生日:</label>
          <input v-model="profile.birthday" id="birthday" type="date" />
        </view>
        <view class="horizontal-divider"></view>
        <view style="display:flex;">
          <label for="introduction" style="font-size: 1em;color: #000;">简介:</label>
          <textarea v-model="profile.introduction" id="introduction" type="text" />
        </view>
        <view class="horizontal-divider"></view>
        <button class="upload" @click="uploadAvatar">上传/更新头像</button>
        <button type="submit" class="save" @click="updateProfile">保存更改</button>
        
      </form>
      
    </view>
  </view>
</template>
  
  <script setup lang="ts">
  import { reactive, ref } from 'vue';
  import { useStatus } from '@/store/status';
  import { HttpManager } from '@/api';
  import type { ResponseBody } from '@/types/interface';
  import {onLoad} from '@dcloudio/uni-app';

  const status = useStatus();
  const showUpdateForm = ref(false);
  const searchUserId = ref('');
  const profile = reactive({
    user_id: status.id,
    sex: '',
    username: '',
    location: '',
    introduction: '',
    birthday: '',
    avatar: '', // Filename of the avatar
  });
  const avatarUrl = ref('');
  const imagePath = ref<string>('');
  const searchUser = async () => {
    try{
      uni.navigateTo({
        url: `/pages/profile/public?id=${encodeURIComponent(searchUserId.value)}`
      })
    }
    catch(error){
      console.log('Error searching user');
    }
  }

  const navigateTo = (url: string) => {
    uni.navigateTo({
      url: url
    })
  }
  const fetchProfile = async () => {
    try {
      const response: ResponseBody = (await HttpManager.getInfo(status.id)) as ResponseBody;
      if (response.success) {
        Object.assign(profile, response.data);
        console.log(profile.avatar);
        if(profile.avatar!==null&&profile.avatar!=''){
          await fetchAvatar();
        }
      } else {
        console.log('Failed to fetch profile');
      }
    } catch (error) {
      console.log('Error fetching profile');
    }
  };
  
  const fetchAvatar = async () => {
    try {
      const bytearray = (await HttpManager.getAvatar({ user_id: status.id })).data as ArrayBuffer;
      console.log('获取头像');
      avatarUrl.value = `data:image/png;base64,${uni.arrayBufferToBase64(bytearray)}`;
    } catch (error) {
      console.log('Error fetching avatar');
    }
  };
  
  const updateProfile = async () => {
    try {
      profile.user_id=status.id;
      console.log(profile);
      const response: ResponseBody = (await HttpManager.updateInfo(profile)) as ResponseBody;
      if (response.success) {
        console.log('Profile updated successfully');
        showUpdateForm.value = false;
      } else {
        console.log('Failed to update profile');
      }
    } catch (error) {
      console.log('Error updating profile');
    }
  };
  
  const uploadAvatar = () => {
    try {
      uni.chooseImage({
        count: 1,
        sizeType: ['original', 'compressed'],
        sourceType: ['album', 'camera'],
        success: (res) => {
          imagePath.value = res.tempFilePaths[0];
          uni.uploadFile({
            url: 'http://192.168.151.176:8080/profile/uploadAvatar',
            filePath: imagePath.value,
            name: 'file',
          formData: {
            user_id: status.id,
            oldFilename: profile.avatar
          },
          success: (uploadRes) => {
          profile.avatar=status.id+'_avatar';
          updateProfile();
          const result = JSON.parse(uploadRes.data);
          if (result.code === 200) {
            console.log('图片上传成功');
          } else {
            console.log('图片上传失败');
          }
        },
        fail: (err) => {
          console.error('Failed to upload picture:', err);
          }
        });
        },
        fail: (err) => {
          console.error('Failed to choose image:', err);
        }
      });
    } catch (error) {
      console.log('Error uploading avatar');
    }
  };
  onLoad(()=>{
    fetchProfile();
  })
  
  </script>
  
  <style scoped>
  input{
    margin-left: 10vw;
  }
  .application-list{
    opacity:0.8;
    border-radius: 10%;
    border: 1px solid #dbaa30;
    box-shadow: 0 0 10px #dbaa30;
    margin-left: 10vw;
  }
  .update{
    margin-right: 10vw;
    opacity:0.8;
    border-radius: 10%;
    border: 1px solid #ce1919;
    box-shadow: 0 0 10px #ce1919;
  }
  .match{
    opacity:0.8;
    border-radius: 10%;
    border: 1px solid #3f98be;
    box-shadow: 0 0 10px #3f98be;
  }
  .upload{
    margin:5vh;
    opacity:0.8;
    border-radius: 10%;
    border: 1px solid #3fbe54;
    box-shadow: 0 0 10px #3fbe54;
  }
  .save{
    margin:5vh;
    opacity:0.8;
    border-radius: 10%;
    border: 1px solid #3fbe54;
    box-shadow: 0 0 10px #3fbe54;
  }
  .search-button{
    font-size: 0.5em;
    margin-right: 10vw;
    opacity:0.8;
    border-radius: 10%;
    border: 1px solid #b3bccf;
    box-shadow: 0 0 10px #b3bccf;
  }
  </style>