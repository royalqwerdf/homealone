package com.elice.homealone.module.chatting.service;

import com.elice.homealone.module.chatting.entity.ChatMessage;
import com.elice.homealone.module.chatting.repository.ChatMessageRepository;
import com.elice.homealone.module.chatting.repository.ChatRoomRepository;
import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    //채팅방 입장 후 전솧한 메시지를 저장하는 메서드
    @Transactional
    public ChatMessage saveMessage(Long chatroomId, Long memberId, String content, Date date, String time, String chatType) {

        //메시지를 보내는 사람
        Member sender = memberRepository.findMemberById(memberId);

        //메시지 저장
        ChatMessage message = ChatMessage.builder()
                .content(content)
                .sendDate(date)
                .sendTime(time)
                .member(sender)
                .chatting(chatRoomRepository.findChattingById(chatroomId))
                .build();
        chatMessageRepository.save(message);

        return message;
    }
}
