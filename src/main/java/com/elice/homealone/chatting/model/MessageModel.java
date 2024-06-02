package com.elice.homealone.chatting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModel {
    private String sender;
    private String content;
    private MessageType type;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageModel(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }
}
