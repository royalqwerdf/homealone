package com.elice.homealone.chatting.entity;

import com.elice.homealone.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    private long id;
    private Member sender;
    private Member receiver;

    private long sender_id;
    private long receiver_id;

    public Chatting toEntity(Member sender, Member receiver) {
        return Chatting.builder()
                .sender(sender)
                .receiver(receiver)
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
