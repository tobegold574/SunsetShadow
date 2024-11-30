<template>
	<view style="display:flex">
    <image src="@/static/049df46a1e123a6ca087d1e0fcbde77c.jpg" class="bg-img"></image>
  	</view>
	<view class="app-name">夕影</view>
	<view class="form-container">
	  <form>
		<view>
		  <view class="label-username">用户名:</view>
		  <input
			class="input"
			v-model="loginForm.username"
			type="text"
			placeholder="用户名"
			required
		  />
		</view>
		<view>
		  <view class="label-password">密码:</view>
		  <input
			class="input"
			v-model="loginForm.password"
			type="password"
			placeholder="密码"
			required
		  />
		</view>
		<button type="submit" class="btn" @click="handleSignIn">登录</button>
    <navigator class="sign-up-link" url="/pages/login/sign-up">注册</navigator>
	  </form>
	</view>
  </template>

<script setup lang='ts'>
import {reactive} from 'vue';
import {HttpManager} from '@/api';
import type {ResponseBody} from '@/types/interface';
import { onLoad } from '@dcloudio/uni-app';
import { useStatus } from '@/store/status';

const status=useStatus();

const loginForm=reactive({
	username:'',
	password:''
})

async function handleSignIn(){
	try{
		const username=loginForm.username;
		const password=loginForm.password;
		
		const result=(await HttpManager.signIn({username:username,password:password})) as ResponseBody;
		
		if(result.success){
			uni.navigateTo({
				url:"/pages/dashboard"
			})
			status.setId(result.data.id);
			status.setUsername(result.data.username);
		}
	}catch(error){
		console.log(error);
	}
}

const navigateToSignup = async () => {
  uni.navigateTo({
    url: "/pages/login/sign-up"
  });
};

onLoad(()=>{
	if(status.id !=-1){
		uni.navigateBack();
	}
})


</script>	

<style scoped>
.app-name {
	font-size: 12vh;
	font-weight: bold;
	text-align: center;
	margin-top:15vh;
	margin-bottom: 5vh;
  }
  
  .form-container {
	margin-top: 6vh;
  }
  
  .label-username {
	padding-left: 20vw;
	display: flex;
	flex-direction:column;
	margin-bottom: 2vh;
  }
  
  .label-password {
	padding-left: 20vw;
	margin-top: 6vh;
	display: flex;
	flex-direction:column;
	margin-bottom: 2vh;
  }
  
  .input {
	border: 1px solid rgb(8, 9, 9);
	height: 5vh;
	margin-left: 20vw;
	margin-right: 20vw;
	width: 60vw;
	box-shadow: 0 0 10px 0 rgba(200, 90, 18, 0.1);
	border-radius: 10%;
  }
  
  button {
	display: flex;
	margin-top: 10vh;
	width: 50vw;
	height: 10vh;
	background-color: rgb(179, 75, 75);
	color: white;
	border-radius:10% ;
	cursor: pointer;
	justify-content: center;
	align-items: center;
  }
  
  button:hover {
	background-color: rgb(179, 75, 75);
  }

  .sign-up-link {
  color: rgb(80, 169, 221);
  display: flex;
  margin-right: 25vw;
  justify-content: flex-end; 
}
</style>