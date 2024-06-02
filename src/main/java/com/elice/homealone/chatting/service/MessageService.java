package com.elice.homealone.chatting.service;

import com.elice.homealone.chatting.entity.ChatMessage;
import com.elice.homealone.chatting.repository.ChatMessageRepository;
import com.elice.homealone.chatting.repository.ChatRoomRepository;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    //채팅방 입장 후 전솧한 메시지를 저장하는 메서드
    @Transactional
    public ChatMessage saveMessage(Long chatroomId, String content, String time, String chatType) {

        //메시지를 보내는 사람
        Member sender = chatRoomRepository.findChattingById(chatroomId).getSender();

        //메시지 저장
        ChatMessage message = ChatMessage.builder()
                .content(content)
                .sendTime(time)
                .member(sender)
                .chatting(chatRoomRepository.findChattingById(chatroomId))
                .build();
        chatMessageRepository.save(message);

        return message;
    }
}
