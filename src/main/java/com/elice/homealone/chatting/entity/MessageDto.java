package com.elice.homealone.chatting.entity;

import com.elice.homealone.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private long id;
    private String content;
    private String sendTime;

    private long memberId;
    private long chatRoomId;
    private Member sender;
    private Chatting chatting;

    public ChatMessage toEntity(Member sender, Chatting chatting) {
        return ChatMessage.builder()
                .content(this.content)
                .sendTime(this.sendTime)
                .member(sender)
                .chatting(chatting)
                .build();
    }
}
