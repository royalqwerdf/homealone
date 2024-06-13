package com.elice.homealone.chatting.entity;

import com.elice.homealone.member.entity.Member;
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

    private Long id;
    private String chatroomName;
    private String senderName;
    private String receiverName;
    private Long senderId;
    private Long receiverId;

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
