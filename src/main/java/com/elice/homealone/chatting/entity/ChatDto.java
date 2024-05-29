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
    private String chatroomName;
    private String senderName;
    private String receiverName;
    private long senderId;
    private long receiverId;

    public Chatting toEntity(Member sender, Member receiver) {
        return Chatting.builder()
                .chatroomName(this.chatroomName)
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
