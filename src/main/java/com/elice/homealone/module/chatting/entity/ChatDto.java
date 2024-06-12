package com.elice.homealone.module.chatting.entity;

import com.elice.homealone.module.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private List<MessageDto> Messages;
    private long currentId;

    public Chatting toEntity(Member sender, Member receiver) {
        return Chatting.builder()
                .chatroomName(this.chatroomName)
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
