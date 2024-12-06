# SunsetShadow
集成UniApp+Typescript+SpringBoot+flask的社交平台，使用pinia、WebSocket、高德api等技术实现各类功能模块😘😘😘欢迎提交优化和指出问题！！！

## 目录结构说明
**前后端文件结构**
```bash
/SunsetShadow
  ├── /xiying-demo-client   # UniApp(api+router)+Vue3+TypeScript
  ├── /xiying-demo-flask    # BERT的api，server的子模块
  ├── /xiying-demo-server   # SpringBoot
  └──  ...
```

**前端文件结构**
```bash
├── /xiying-demo-client
    ├── /src
    │   ├── /api            # 包装HTTP请求，以及包装api和HTTP请求
    │   ├── /pages          # Vue代码，页面级组件
    │   ├── /static         # 在上传github时已略去，用于存储背景图片等静态资源，可自定义
    │   ├── /store          # 使用pinia全局管理用户个人信息，便于各个页面访问
    │   ├── /types          # 接口定义，以及重写STOMP和包装UniAppWebSocket
    │   ├── App.vue         # 全局样式，全局钩子
    │   ├── env.d.ts        # 使UniApp框架和Vue框架互相兼容（项目自动生成）
    │   ├── main.ts         # 程序启动入口，App实例化和pinia实例化
    │   ├── manifest.json   # UniApp的打包配置，除应用ID以外，外部接口都需要自己重新申请
    │   ├── pages.json      # UniApp的router，路由管理
    │   ├── shime-uni.d.ts  # 
    │   ├── uni.scss
    
    



