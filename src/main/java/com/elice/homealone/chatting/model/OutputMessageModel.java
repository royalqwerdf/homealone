package com.elice.homealone.chatting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class OutputMessageModel {

    private String content;
    private String sendTime;


    public OutputMessageModel(String content, String sendTime){

        this.content = content;
        this.sendTime = sendTime;
    }
}
