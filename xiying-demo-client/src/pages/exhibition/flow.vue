<template>
    <view class="swiper-container">
      <swiper vertical :circular="true" @change="onSwiperChange" :loop="true" class="swiper-container">
        <swiper-item v-for="(image, index) in images" :key="index" class="swiper-item">
          <image :src="image.url" mode="aspectFill" class="swiper-image" />
          <uni-icons type="chat" size="50" color="#fff" @click="showComment = !showComment" class="chat-icon"></uni-icons>
          <view v-if="showComment" class="comment-container">
            <ul v-if="comments[image.filename]" class="comment-list">
              <li v-for="(comment, index) in comments[image.filename].comments" :key="comment.id" class="comment-item">
                <text style="font-size: 1em;color: #fff;">{{ comments[image.filename].usernames[index] }}:</text>
                <text style="font-size: 1em;color: #fff;">  {{ comment.comment }}</text>
              </li>
            </ul>
           </view>
           <view v-if="showComment" class="comment-input">
              <input v-model="newComment" placeholder="分享你的感受" />
              <uni-icons type="chatbubble" size="50" color="#fff" @click="submitComment(image.filename)" class="submit-button"></uni-icons>
            </view>
        </swiper-item>
      </swiper>
    </view>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  import { HttpManager } from '@/api/index'
  import type { ResponseBody } from '@/types/interface';
  import { onLoad } from '@dcloudio/uni-app';
  import { useStatus } from '@/store/status';

  const statusStore = useStatus();
  const user_id = statusStore.id;
  const images = ref<{ filename: string,url:string }[]>([])
  const currentIndex = ref(0)
  const comments = ref<{ [key: string]: { comments: { id: number, comment: string }[], usernames: string[] } }>({})
  const newComment = ref('')
  const showComment = ref(false)

 const fetchRandomImages = async () => {
  try {
    const bytearray = (await HttpManager.getRandomImage()).data as ArrayBuffer;
    const filename = (await HttpManager.getRandomImageFilename()) as string;
    const url = `data:image/png;base64,${uni.arrayBufferToBase64(bytearray)}`;
    return { filename, url };
  } catch (error) {
    console.error('Error fetching images:', error);
    return null;
  }
};

  const loadMoreImages = async () => {
    const newImage = await fetchRandomImages()
    if (newImage) {
      images.value.push(newImage)
      fetchComments(newImage.filename)
    }
  }

  const fetchComments = async (imageFilename: string) => {
    try {
      const response = await HttpManager.getCommentsByPic({ pic_name: imageFilename }) as ResponseBody;
      comments.value[imageFilename] = response.data
      console.log(comments)
    } catch (error) {
      console.error('Error fetching comments:', error)
    }
  }

  const submitComment = async (filename: string) => {
    try {
      await HttpManager.addComment({ pic_name: filename, comment: newComment.value,user_id: user_id })
      fetchComments(filename)
      newComment.value = ''
    } catch (error) {
      console.error('Error submitting comment:', error)
    }
  }

  const onSwiperChange = (e: any) => {
    currentIndex.value = e.detail.current
    if (currentIndex.value === images.value.length - 1) {
      loadMoreImages()
    }
  }


  onLoad(async () => {
    while(images.value.length < 3) {
      await loadMoreImages()
    }
  })
  </script>
  
  <style>
  .comment-container {
    display: flex;
    align-items: left;
    bottom: 10vh;
    margin-bottom: 10vh;
    margin-top: 70vh;
  }
  .swiper-image {
    width: 100vw;
    height: 100vh;
    object-fit: cover;
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
  }
  .swiper-container {
    width: 100vw;
    height: 100vh;
  }
  .swiper-item {
    width: 100vw;
    height: 100vh;
  }
  .submit-button {
    margin-left:85vw;
    display: flex;
  }
  .chat-icon {
    position: absolute;
    margin-top: 75vh;
    margin-left: 85vw;
  }

  .comment-item {
    display: flex;
    align-items: left;
    margin: 1vh;
  }
  .comment-list {
    display: flex;
    flex-direction: column;
    align-items: left;
    position:absolute;
  }

  .comment-input {
    margin-top: 10vh;
    display: flex;
    width: 100%;
    align-items: center;
    position:absolute;
    border:1px solid #ec9704;
    border-radius: 10%;
  }
  </style>