package com.elice.homealone.socket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class OutputMessageModel {
    private String sender;
    private String chatUuid;
    private String content;
    private String time;
    private MessageType type;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    public OutputMessageModel(String sender, String chatUuid, String content, String time, MessageType type) {
        this.sender = sender;
        this.chatUuid = chatUuid;
        this.content = content;
        this.time = time;
        this.type = type;
    }
}
