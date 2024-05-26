package com.elice.homealone.global.socket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class OutputMessageModel {
    private Long chatroomId;
    private Long senderId;
    private String senderName;
    private String content;
    private String time;
    private MessageType type;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    public OutputMessageModel(Long chatroomId, Long senderId, String senderName, String content, String time, MessageType type) {
        this.chatroomId = chatroomId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.content = content;
        this.time = time;
        this.type = type;
    }
}
