# SunsetShadow
集成UniApp+Typescript+SpringBoot+flask的社交平台，使用pinia、WebSocket、高德api等技术实现各类功能模块😘😘😘欢迎提交优化和指出问题！！！

## 项目亮点说明
### 跨端实时通信(redis+WebSocket+Stomp)
- 技术栈较为罕见（完全使用redis实现消息队列）
- 使用 **重写** StompClient的方法解决了安卓对Stomp无法兼容的问题（Stomp内部钩子在安卓环境下无法触发）
- 使用 **静态类** 避免了重复的WebSocket连接
### 位置共享(高德API)
- 使用UniApp框架提供的API获取用户拍照时的信息位置，并将其共享和可视化
### 抖音式动态浏览
- 可以添加评论
- 可以翻看他人分享照片
### 交友系统
- 可以向他人发出好友申请
- 查询用户ID
### 交友匹配(BERT)
- 可以输入标签确定想要匹配的好友范围，然后匹配好友（基于个人自我介绍的相似度）


## 目录结构说明
**前后端文件结构**
```bash
/SunsetShadow
  ├── /xiying-demo-client   # UniApp(api+router)+Vue3+TypeScript
  ├── /xiying-demo-flask    # BERT的api，相当于SpringBoot的子模块
  ├── /xiying-demo-server   # SpringBoot
  └──  ...
```
以上三个部分都是严格遵从分离思想的，都可以独立启动，和其他的前/后端项目共同使用。
***

**前端文件结构说明**
```bash
├── /xiying-demo-client
    ├── /src
    │   ├── /api            # 使用UniApp的API构建HTTP，以及合成前后端交互
    │   ├── /pages          # Vue代码，页面级组件
    │   ├── /static         # 在上传github时已略去，用于存储背景图片等静态资源，可自定义
    │   ├── /store          # 使用pinia全局管理用户个人信息，便于各个页面访问
    │   ├── /types          # 接口定义，以及重写STOMP和包装UniAppWebSocket
    │   ├── App.vue         # 全局样式，全局钩子
    │   ├── env.d.ts        # 告诉TypeScript如何处理Vue文件
    │   ├── main.ts         # 程序启动入口，App实例化和pinia实例化
    │   ├── manifest.json   # UniApp的打包配置，除应用ID以外，外部接口都需要自己重新申请
    │   ├── pages.json      # UniApp的router，路由管理
    │   ├── shime-uni.d.ts  # 兼容UniApp的实例和Vue，以便Vue文件中可以使用UniApp框架提供的API(子模块)
    │   └── uni.scss        # UniApp内置样式变量
    ├── index.html          # SPA（单页面应用）的启动入口
    ├── shims-uni.d.ts      # 兼容UniApp的实例和Vue，以便Vue文件中可以使用UniApp框架提供的API(主目录)
    ├── tsconfig.json       # TypeScript的配置文件
    └── vite.config.ts      # Vite配置，使用uni插件
```
注意`static`文件夹是我之前用来存放页面背景的，相关的css如何配置我没有删除，可以直接创建新的`static`文件，然后用自己的图片路径对每个页面中的原来的路径进行覆盖😯。

***
**后端文件结构说明(SpringBoot)**
```bash
├── /xiying-demo-server
    ├── /xiying-server
    │   ├── /sql                          # 用于存放数据库构建脚本(MySQL)
    │   ├── /src
    │   │    ├── /main
    │   │    │     ├── ...                # JAVA的包命名规范
    │   │    │     │    ├── /common       # 常用接口定义文件，比如Response
    │   │    │     │    ├── /config       # 其他服务布局，比如OSS和WebSocket
    │   │    │     │    ├── /constant     # 常用常量，如加密盐值
    │   │    │     │    ├── /controller   # controller层
    │   │    │     │    ├── /handler      # meta数据处理，如数据库中的时间变量
    │   │    │     │    ├── /mapper       # mapper层
    │   │    │     │    ├── /model        # model层
    │   │    │     │    ├── /service      # service层
    │   │    │     │    ├── /utils        # 工具类，比如加密、随机
    │   │    │     │    └── /interceptors # 握手中的额外操作，比如认证
    │   │    │     └── xiyingApplication  # 启动！
    │   └── pom                           # 依赖
```
部分接口未在前端实装，请注意。

***
**后端子模块文件结构说明(flask)** 
```bash
├── /xiying-demo-flask
    ├── /app
    │    ├── __init__                      # flask实例
    │    ├── routes                        # 接口
```
这里对flask的应用较简单，主要是考虑到要使用模型，所以只是作为SpringBoot的一个子模块，相当于仍然 **只有** SpringBoot在和前端交互，而flask会向SpringBoot提供接口。

