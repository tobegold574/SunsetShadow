package com.demo.xiying.config;


import java.net.URI;
import java.security.Principal;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import com.demo.xiying.utils.StompPrincipal;
import com.demo.xiying.interceptors.CustomHandshakeHandler;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
     * 每一行代码的作用：
     * 注册stomp端点
     * stomp端点是客户端与服务器进行通信的入口点
     * 允许所有来源的请求
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/chat")
                .setAllowedOrigins("*")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        URI uri = request.getURI();
                        Map<String, String> queryParams = UriComponentsBuilder.fromUri(uri).build().getQueryParams().toSingleValueMap();

                        // 从查询参数中获取用户名
                        String username = queryParams.get("username");
                        if (username != null) {
                            Principal principal = new StompPrincipal(username);
                            attributes.put("principal", principal);
                            System.out.println("用户名（用于绑定用户目的地）: " + username);
                        } else {
                            System.out.println("查询参数中未找到用户名。");
                        }
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
                        System.out.println("WebSocket Handshake end: " + (ex == null ? "Success" : "Failed: " + ex.getMessage()));
                    }
                })
                .setHandshakeHandler(new CustomHandshakeHandler());
    }

    /*
     * 每一行代码的作用：
     * 设置应用程序目的地前缀
     * 启用简单代理
     * 设置用户目的地前缀
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setUserDestinationPrefix("/user");
    }
}
