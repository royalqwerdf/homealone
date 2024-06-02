package com.elice.homealone.chatting.service;

import com.elice.homealone.chatting.entity.ChatDto;
import com.elice.homealone.chatting.entity.ChatMessage;
import com.elice.homealone.chatting.entity.Chatting;
import com.elice.homealone.chatting.entity.MessageDto;
import com.elice.homealone.chatting.repository.ChatMessageRepository;
import com.elice.homealone.chatting.repository.ChatRoomRepository;
import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.member.service.AuthService;

import com.google.firebase.cloud.StorageClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;


    //중고거래 채팅방 생성 메소드
    @Transactional
    public ChatDto makeChat(String accessToken, ChatDto chatDto) {
        Long receiver_id = chatDto.getReceiverId();
        Member receiver = memberRepository.findMemberById(receiver_id);

        //Member 도메인 회원 조회 메소드 참고
        Member member = authService.findLoginMemberByToken(accessToken);
        Member sender = memberRepository.findMemberByEmail(member.getEmail());

        if(receiver_id == sender.getId()) {
            throw new HomealoneException(ErrorCode.CHATROOM_CREATION_FAILED);
        }

        //chatting 테이블 생성해 저장
        Chatting chatroom = chatRoomRepository.save(chatDto.toEntity(sender, receiver));
        chatDto.setId(chatroom.getId());
        chatDto.setSenderName(chatroom.getSender().getName());
        chatDto.setReceiverName(chatroom.getReceiver().getName());
        chatDto.setSenderId(chatroom.getSender().getId());

        return chatDto;
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
    public ChatDto findChatList(Long chatroomId) {

        //chatroomId에 따른 채팅방이 존재하지 않으면 예외 던지기
        Chatting chatting = chatRoomRepository.findById(chatroomId).orElseThrow(() ->
                new HomealoneException(ErrorCode.CHATTING_ROOM_NOT_FOUND));

        //sender의 메시지 dto 리스트
        List<MessageDto> senderChatList = chatMessageRepository.findAllChatMessageByChattingIdAndMemberId(chatroomId, chatting.getSender().getId());

        //receiver의 메시지 dto 리스트
        List<MessageDto> receiverChatList = chatMessageRepository.findAllChatMessageByChattingIdAndMemberId(chatroomId, chatting.getReceiver().getId());

        ChatDto responseDtos = ChatDto.builder()
                .id(chatroomId)
                .chatroomName(chatting.getChatroomName())
                .senderName(chatting.getSender().getName())
                .receiverName(chatting.getReceiver().getName())
                .senderMessages(senderChatList)
                .receiverMessages(receiverChatList)
                .build();

        return responseDtos;
    }

    @Transactional
    public List<ChatDto> findChatrooms(String accessToken) {
        if(accessToken == null || accessToken.isEmpty()) {
            throw new HomealoneException(ErrorCode.NO_JWT_TOKEN);
        }

        //Member 도메인 회원 조회 메소드 참고
        Member member = authService.findLoginMemberByToken(accessToken);
        Member sender = memberRepository.findMemberByEmail(member.getEmail());

        List<Chatting> chattings = chatRoomRepository.findAllChattingBySenderId(sender.getId());
        List<ChatDto> chatDtoList = new ArrayList<>();
        for(Chatting chatting : chattings) {
            chatDtoList.add(chatting.toDto());
        }

        return chatDtoList;
    }
}
