package com.elice.homealone.module.chatting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModel {
    private Long memberId;
    private String content;
    private Long chatroomId;
    private MessageType type;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageModel(Long memberId, String content, Long chatroomId, MessageType type) {
        this.memberId = memberId;
        this.content = content;
        this.chatroomId = chatroomId;
        this.type = type;
    }
}
