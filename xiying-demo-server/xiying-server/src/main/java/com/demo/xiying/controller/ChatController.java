package com.demo.xiying.controller;

import com.demo.xiying.common.R;
import com.demo.xiying.model.domain.ChatMessage;
import com.demo.xiying.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
/*
 * 聊天控制器
 */
@Controller
@CrossOrigin(origins = "*")
public class ChatController {

    @Value("${redis.channel.message}")
    private String message;

    @Value("${redis.set.onlineUsers}")
    private String onlineUsers;

    /*
     * 注入redisTemplate
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /*
     * 注入simpMessageSendingOperations
     */
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /*
     * 发送消息
     */
    @MessageMapping("/chat.sendMessage")
    public R sendMessage(@Payload ChatMessage chatMessage) {
        try {
            //把消息发到redis存起来
            redisTemplate.convertAndSend(message, JsonUtils.parseObjToJson(chatMessage));
            //发到公共频道
            messagingTemplate.convertAndSend("/topic/public", chatMessage);

            System.out.println("sendMessage(public):" + chatMessage.getSender());
        } catch (Exception e) {
            return R.error("信息发送失败");
        }
        return R.success("消息发送成功");
    }

    /*
     * 发送私信
     */
    @MessageMapping("/chat.sendPrivateMessage")
    public R sendPrivateMessage(@Payload ChatMessage chatMessage) {
        try {
            //可以与configuration中的setUserDestinationPrefix对应
            String recipient = chatMessage.getRecipient();
            //内嵌了setUserDestinationPrefix方法，使消息发送到指定用户
            messagingTemplate.convertAndSendToUser(recipient, "/queue/messages", chatMessage);

            System.out.println("sendPrivateMessage：" + recipient);
        } catch (Exception e) {
            return R.error("私信发送失败");
        }
        return R.success("私信发送成功");
    }

    /*
     * 添加用户
     */
    @MessageMapping("/chat.addUser")
    public R addUser(@Payload ChatMessage chatMessage) {
        try {
            //把用户添加到redis的onlineUsers集合中
            Long result = redisTemplate.opsForSet().add(onlineUsers, chatMessage.getSender());
            System.out.println("addUser:" + result);
            //新用户加入的消息存到redis
            redisTemplate.convertAndSend(message, JsonUtils.parseObjToJson(chatMessage));
            //把有新用户加入的消息通过websocket发到公共频道
            messagingTemplate.convertAndSend("/topic/public", chatMessage);

            return R.success(chatMessage.getSender() + "来了");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}
