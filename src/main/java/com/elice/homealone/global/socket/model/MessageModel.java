package com.elice.homealone.global.socket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModel {
    private Long senderId;
    private String content;

    public MessageModel(Long senderId, String content) {
        this.senderId = senderId;
        this.content = content;
    }
}
