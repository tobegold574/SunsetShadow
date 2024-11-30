<template>
  <view class="editor-container">
    <image :src="imagePath" mode="aspectFit" class="photo-preview"></image>
    <form >
      <textarea v-model="introduction" placeholder="Enter introduction"></textarea>
      <button type="submit" @click="submitPicAndDescription" >上传</button>
    </form>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { HttpManager } from '@/api/index';
import type { ResponseBody } from '@/types/interface';
import { onLoad } from '@dcloudio/uni-app';
import { useStatus } from '@/store/status';

const status = useStatus();

const imagePath = ref<string>('');
const introduction = ref<string>('');
const longitude=ref<number>(0)
const latitude=ref<number>(0)

onLoad((options: any) => {
  imagePath.value = decodeURIComponent(options.imagePath as string);
});

const submitPicAndDescription = async () => {
  if (!imagePath.value) {
    uni.showToast({
      title: '请先拍照',
      icon: 'none'
    });
    return;
  }

  try {
    console.log(introduction.value);
    // Upload the picture
    const uploadResult = await new Promise((resolve, reject) => {
      if(!status.shareLocation){
      uni.showToast({
        title: '是否愿意分享到地图',
        icon: 'none',
        duration: 3000,
        success: (res) => {
          console.log('用户点击了确定');
          status.setShareLocation(true)
        },
        fail: (err) => {
          console.log('用户点击了取消');
        }
      });
      if(status.shareLocation){
        const location=uni.getLocation({
          type: 'wgs84',
          success: async (res) => {
            longitude.value=res.longitude
            latitude.value=res.latitude
            console.log("位置信息获取成功")
            const result=await HttpManager.addMarker({
              user_id:status.id,
              longitude:longitude.value,
              latitude:latitude.value,
              description:introduction.value
            }) as ResponseBody
            if(result.success){
              console.log("位置信息上传成功")
            }
          },
          fail: (err) => {
            console.log("位置信息获取失败")
          }
        })
      }
      uni.uploadFile({
        url: 'http://localhost:8080/pic/uploadPic',
        filePath: imagePath.value,
        name: 'file',
        formData: {
          user_id: status.id
        },
        success: (uploadRes) => {
          const result = JSON.parse(uploadRes.data);
          if (result.code === 200) {
            resolve(result);
          } else {
            reject(new Error('图片上传失败'));
          }
        },
        fail: (err) => {
          console.error('Failed to upload picture:', err);
          reject(err);
        }
      });
    }
  })


    const response = await HttpManager.setIntroduction({
      user_id: status.id,
      pic: imagePath.value,
      introduction: introduction.value
    }) as ResponseBody;

    if (response.success) {
      uni.showToast({
        title: '上传成功',
        icon: 'success'
      });
      uni.navigateBack()
    } else {
      uni.showToast({
        title: '上传失败',
        icon: 'none'
      });
    }
  } catch (error) {
    console.error('Error during upload or saving introduction:', error);
    uni.showToast({
      title: '上传失败',
      icon: 'none'
    });
  }
};
</script>

<style>
.editor-container {
  padding: 20px;
}
.photo-preview {
  width: 100%;
  height: 300px;
  margin-bottom: 20px;
}
textarea {
  width: 100%;
  height: 100px;
  margin-bottom: 10px;
}
button {
  width: 100%;
}
</style>