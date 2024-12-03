package com.demo.xiying.controller;

import com.demo.xiying.common.R;
import com.demo.xiying.model.domain.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.net.Inet4Address;
import java.net.InetAddress;

@Component
public class WebSocketEventListener {

    @Value("${server.port}")
    private String serverPort;

    @Value("${redis.set.onlineUsers}")
    private String onlineUsers;

    @Value("${redis.channel.message}")
    private String message;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @EventListener
    public R handleWebSocketConnectListner(SessionConnectedEvent event) {
        try {
            InetAddress localhost = Inet4Address.getLocalHost();
            return R.success("成功从" + localhost + "获取连接");
        } catch (Exception e) {
            // Add detailed logging
            return R.error("Error fetching connection: " + e.getMessage());
        }
    }

    @EventListener
    public R handleWebSocketDisconnetListner(SessionDisconnectEvent sessionDisconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.LEAVE);
        chatMessage.setSender(username);

        try {
            redisTemplate.opsForSet().remove(onlineUsers, username);
            return R.success(username + "已离开");
        } catch (Exception e) {
            // Add detailed logging
            return R.error("连接故障: " + e.getMessage());
        }
    }
}
