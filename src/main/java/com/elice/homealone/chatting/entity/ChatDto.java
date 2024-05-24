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
    private String chatroom_name;
    private String chatUuid;
    private Member sender;
    private Member receiver;

    private String member_email;
    private long sender_id;
    private long receiver_id;

    public Chatting toEntity(Member sender, Member receiver) {
        return Chatting.builder()
                .chatroom_name(this.chatroom_name)
                .chatUuid(this.chatUuid)
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
