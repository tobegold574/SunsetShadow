import { getBaseUrl, get, post } from "./request";

export const HttpManager = {
	// 登录
	signIn: ({ username, password }) => post("/login/enter", { username, password }),
	// 注册
	signUp: ({ username, password }) => post("/login/registry", { username, password }),
	// 更新个人信息
	updateInfo: ({ user_id, username, sex, location, introduction, birthday,avatar }) => post("/profile/updateInfo", { user_id, username, sex, location, introduction, birthday,avatar }),
	// 获取个人信息（更新后）
	getInfo: (user_id: number) => post("/profile/getInfo", { user_id: user_id }),
	// 获取头像
	getAvatar: ({ user_id }) => {
		return uni.request({
			url: `${getBaseUrl()}/image/avatars/${user_id}`,
			method: 'GET',
			responseType: 'arraybuffer',
		});
	},
	//在好友页面获取好友列表
	getFriends: (user_id: number) => post("/relation/getFriends", { user_id: user_id }),
	//在个人页面获取好友申请列表
	getApplicationList: (receiver_id: number) => post("/application/list", { receiver_id: receiver_id }),
	//在他人信息页面发送好友申请
	sendApplication: ({ sender_id, receiver_id }) => post("/application/send", { sender_id: sender_id, receiver_id: receiver_id }),
	//在个人页面处理好友申请
	updateApplication: ({ sender_id, receiver_id, accept }) => post("/application/update", { sender_id, receiver_id, accept }),
	// 获取随机图片
	getRandomImage: async () => {
		return uni.request({
			url: `${getBaseUrl()}/api/images/random`,
			method: 'GET',
			responseType: 'arraybuffer',
		});
	},
	// 获取图片评论
	getCommentsByPic: ({ pic_name }) => post("/comment/public", { pic_name }),
	// 添加图片评论
	addComment: ({ pic_name, comment, user_id }) => post("/comment/public/send", { pic_name, comment, user_id }),
	//添加图片介绍
	setIntroduction: ({ user_id, pic, introduction }) => post("/pic/setIntroduction", { user_id, pic, introduction }),
	// 在个人页面搜索他人的公共页面
	findById: ({ user_id }) => get("/profile/getById", { user_id }),
	// 获取随机图片的文件名
	getRandomImageFilename: () => get("/api/images/random/filename"),
	// 匹配
	match: ({ user_id, sex, location }) => post("/profile/match", { user_id, sex, location }),
	// 添加标记
	addMarker: ({ user_id, description, longitude, latitude }) => post("/marker/add", { user_id, description, longitude, latitude }),
	// 获取所有标记
	getAllMarkers: () => get("/marker/get"),
}

