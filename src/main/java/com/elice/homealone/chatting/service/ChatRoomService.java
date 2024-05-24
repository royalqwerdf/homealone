package com.elice.homealone.chatting.service;

import com.elice.homealone.chatting.entity.ChatDto;
import com.elice.homealone.chatting.entity.ChatMessage;
import com.elice.homealone.chatting.entity.Chatting;
import com.elice.homealone.chatting.repository.ChatMessageRepository;
import com.elice.homealone.chatting.repository.ChatRoomRepository;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    //중고거래 채팅방 생성 메소드
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

    //채팅방에서 전송된 메시지를 저장하는 메소드. 채팅방과 1:n
    @Transactional
    public ChatMessage saveMessage(Long chatroomId, Long senderId, String content, String time) {
        ChatMessage chatMessage = ChatMessage.builder()
                .content(content)
                .member(memberRepository.findMemberById(senderId))
                .sendTime(time)
                .chatting(chatRoomRepository.findChattingById(chatroomId))
                .build();
        chatMessageRepository.save(chatMessage);

        return chatMessage;
    }

    @Transactional
    public List<ChatMessage> findChatList(Long chatroomId, Long memberId) {
        return chatMessageRepository.findAllChatMessageByChattingIdAndMemberId(chatroomId, memberId);
    }
}
