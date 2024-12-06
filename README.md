# 夕影(SunsetShadow)
集成UniApp+SpringBoot+flask的跨端（暂时h5和android）自然系轻型社交平台，使用pinia、WebSocket、高德api等技术实现各类功能模块😘😘😘欢迎提交优化和指出问题❤️❤️❤️！！！

## 项目亮点说明
### 跨端实时聊天(redis+WebSocket+Stomp)
- 技术栈较为罕见（完全使用redis实现消息队列）
- 使用 **重写** StompClient的方法解决了安卓对Stomp无法兼容的问题（Stomp内部钩子在安卓环境下无法触发）
- 使用 **静态类** 避免了重复的WebSocket连接
### 位置共享(高德API)
- 使用UniApp框架提供的API获取用户拍照时的信息位置，并将其共享和可视化
### 拍照分享(MinIO)
- 用户可以随时拍照上传，添加文字描述，分享动态
### 抖音式动态浏览(MinIO)
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
    │   ├── /sql                                    # 用于存放数据库构建脚本(MySQL)
    │   ├── /src
    │   │    ├── /main
    │   │    │     ├── /java
    │   │    │     │    ├── /com/demo/xiying        # JAVA的包命名规范
    │   │    │     │        ├── /common             # 常用接口定义文件，比如Response
    │   │    │     │        ├── /config             # 其他服务布局，比如OSS和WebSocket
    │   │    │     │        ├── /constant           # 常用常量，如加密盐值
    │   │    │     │        ├── /controller         # controller层
    │   │    │     │        ├── /handler            # meta数据处理，如数据库中的时间变量
    │   │    │     │        ├── /mapper             # mapper层
    │   │    │     │        ├── /model              # model层
    │   │    │     │        ├── /service            # service层
    │   │    │     │        ├── /utils              # 工具类，比如加密、随机
    │   │    │     │        ├── /interceptors       # 握手中的额外操作，比如认证              
    │   │    │     │        └── xiyingApplication   # 启动！
    │   │    └── /resources                         # 本地数据库及OSS等配置
    │   └── pom                                     # 依赖
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

## 本地运行
### 前端运行
建议使用 **HBuilderX** 进行调试， **HBuilderX** 与UniApp框架紧密联系，为UniApp项目提供更便利的调试支持。在 **HBuilderX** 内可以直接选择 **在浏览器中运行** 。
或者使用cli：
```bash
npm run install             # 安装依赖
npm run dev:%platform%      # 运行至不同平台
```
- 注意，当前前端内 **没有测试账户** ，如果只运行前端就 **只能访问登录页面** ，建议 **先启动后端** 。
更多问题请咨询csdn或[uni-app](https://uniapp.dcloud.net.cn/)
***

### 后端运行(SpringBoot)
后端运行需要 **几个预启动服务、日志配置、数据库后端交互框架配置** ：
- 预启动服务
1. MinIO服务
2. MySQL数据库
3. redis数据库
- 日志及后端内部框架
1. log4j
2. mybatis-plus

建议创建`resources`文件夹存放各类配置，具体 **如何配置** 请参考文件夹`/resources-reference`。

以上 **全部完成** 之后请切换目录到你的`/xiying-server`文件夹。然后：
```bash
mvn install             # 安装依赖
mvn spring-boot:run     # 启动！
```
（注意请一句一句指令输入回车，等待上一条指令工作完成，再进行下一条指令╰(*°▽°*)╯）
***
### 后端运行(flask)
请切换到`xiying-demo-flask`目录。然后输入
```bash
python run.py
```
即可启动BERT模型服务。

### 安卓模拟运行特别提醒
如果前端(UniApp)运行在Android Studio上，则请将前端访问的后端地址改为 **本机ipv4地址** 。
请通过
```cmd
ipconfig
```
自行查看自己的ipv4地址为多少（参考csdn资料）。

## 项目初衷与后续想法
### 我开发夕影的一点点想法
在当下这个快节奏的社会，人们的生活被各种各样的信息所冲击，在各种娱乐之中解渴与渴求，我认为这样的生活状态需要得到一定的矫正，即使是如此复杂的社会，如此复杂的人类，我们的心中应该仍然保持了一片澄澈而纯净的地方，自然的美景可以填充那里，也可以洗去尘气与疲倦。

正如朱光潜先生的《谈美》中所说到的，“人要有出世的精神才可以做入世的事业”，夕影的开发初衷便是为了给用户提供一片脱俗之地，让纯净的心灵以自然为桥梁聚拢，让自然弥补当下复杂的社会所带来的拥挤感与空虚感，你可以独赏美景，亦可以与他人分享，你可以与其他人一同探寻，也可以独自前往。

似乎只是为了夕阳景色这一点，显得有些幼稚，但我认为，简单而澄澈的美才是解决当下人们庸碌而迷惘的最佳灵药。

“慢慢走，欣赏啊！”
“大人者不失其赤子之心”
我认为朱光潜先生所说的是极对的，在纯粹的美中生活，能够帮助抵抗生活的重压，活出生命应有的样子。
***
### 后续想法
夕影当前还是一个非常简陋的样子，我之前在发现基本壳子出来之后，接下来的路更加坎坷时，就轻易放弃了。我接下来准备从至少这么几个方面打磨它，一方面是提升技术，一方面是完成我的初衷和一点点对于开发的喜爱吧，还有一方面是打算为后面找日常实习做准备。
- 条件编译，支持更多平台
- 更多动态精美的ui组件（如gsap）
- 加入更多事务级别操作，对应用后台进行更细粒度的管理(AOP)
- 更细粒度的后端与数据库交互，应对高并发
- 制作一个完整的后台管理系统

***

**如果你喜欢我的想法的话，请给我一个Star吧o(*￣︶￣*)o**

这是我的[个人博客](https://tobegold574.me/)，我已经记录了leetcode hot100的做题过程，我会每天更新每日一题的做题过程和总结，再之后还将加入我的开发日志，和我在AI方面学习到的新知识和论文进展等等，请关注我吧😲。我也会专门放一篇博客用来讨论哦，如果大家有任何关于夕影、实习、开发的想法，欢迎评论留言ヾ(•ω•`)o！

[直通讨论区](https://tobegold574.me/2024/12/06/%E5%A4%95%E5%BD%B1%E8%AE%A8%E8%AE%BA%E5%8C%BA-%E2%9D%81%C2%B4%E2%97%A1-%E2%9D%81/)

## 参考与鸣谢
- [spring](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/context-hierarchy.html)
- [uniapp](https://uniapp.dcloud.net.cn/)
- [vue](https://cn.vuejs.org/guide/quick-start.html)
- [TypeScript](https://www.tslang.cn/docs/handbook/advanced-types.html)
- [哈工大讯飞实验室的miniRBT](https://github.com/iflytek/MiniRBT)
- 以及各种依赖和服务！（可能还有重要的忘了没列在这(‾◡◝)）
