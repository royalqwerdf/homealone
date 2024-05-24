package com.elice.homealone.chatting.service;

import com.elice.homealone.chatting.entity.ChatDto;
import com.elice.homealone.chatting.entity.Chatting;
import com.elice.homealone.chatting.repository.ChatRoomRepository;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Chatting makeChat(ChatDto chatDto) {
        Long receiver_id = chatDto.getReceiver_id();
        Member receiver = memberRepository.findMemberById(receiver_id);
        String sender_email = chatDto.getMember_email();
        Member sender = memberRepository.findMemberByEmail(sender_email);

        Chatting chatRoom = chatDto.toEntity(receiver, sender);
        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }
}
