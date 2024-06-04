package com.elice.homealone.chatting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModel {
    private String sender;
    private String content;
    private Long chatroomId;
    private MessageType type;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageModel(String sender, String content, Long chatroomId, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.chatroomId = chatroomId;
        this.type = type;
    }
}
