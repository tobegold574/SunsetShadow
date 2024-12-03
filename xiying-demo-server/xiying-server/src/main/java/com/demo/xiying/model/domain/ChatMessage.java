package com.demo.xiying.model.domain;


import lombok.Data;

@Data
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;
    private String recipient;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
