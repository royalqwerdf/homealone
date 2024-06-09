package com.elice.homealone.chatting.service;

import com.elice.homealone.chatting.entity.ChatDto;
import com.elice.homealone.chatting.entity.ChatMessage;
import com.elice.homealone.chatting.entity.Chatting;
import com.elice.homealone.chatting.entity.MessageDto;
import com.elice.homealone.chatting.repository.ChatMessageRepository;
import com.elice.homealone.chatting.repository.ChatRoomRepository;
import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.dto.MemberDto;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.member.service.AuthService;

import com.elice.homealone.member.service.MemberService;
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
    private final MemberService memberService;


    //중고거래 채팅방 생성 메소드
    @Transactional
    public ChatDto makeChat(Member member, ChatDto chatDto) {
        Long receiver_id = chatDto.getReceiverId();
        Member receiver = memberRepository.findMemberById(receiver_id);

        //현재 로그인된 사용자 정보
        if(member == null) {
            throw new HomealoneException(ErrorCode.MEMBER_NOT_FOUND);
        }
        Long curMemberId = member.getId();

        if(receiver_id == curMemberId) {
            throw new HomealoneException(ErrorCode.CHATROOM_CREATION_FAILED);
        }

        //chatting 테이블 생성해 저장
        Chatting chatroom = chatRoomRepository.save(chatDto.toEntity(member, receiver));

        return chatroom.toDto();
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
    public ChatDto findChatList(Member member, Long chatroomId) {

        //현재 로그인된 사용자 정보
        Long curMemberId = member.getId();

        //chatroomId에 따른 채팅방이 존재하지 않으면 예외 던지기
        Chatting chatting = chatRoomRepository.findById(chatroomId).orElseThrow(() ->
                new HomealoneException(ErrorCode.CHATTING_ROOM_NOT_FOUND));
        //현재 로그인한 회원이 자신이 속해있지 않은 채팅방 id를 통해 접근하려할 때
        if(chatting.getSender().getId() != curMemberId && chatting.getReceiver().getId() != curMemberId) {
            throw new HomealoneException(ErrorCode.NOT_MY_CHATROOM);
        }

        //채팅 참여자들의 메시지 dto 리스트
        List<ChatMessage> senderChatList = chatMessageRepository.findAllChatMessageByChattingIdOrderBySendDateAsc(chatroomId);
        List<MessageDto> Messages = new ArrayList<>();
        for(ChatMessage message : senderChatList) {
            Messages.add(message.toDto());
        }



        ChatDto responseDtos = ChatDto.builder()
                .id(chatroomId)
                .chatroomName(chatting.getChatroomName())
                .senderName(chatting.getSender().getName())
                .receiverName(chatting.getReceiver().getName())
                .senderId(chatting.getSender().getId())
                .receiverId(chatting.getReceiver().getId())
                .Messages(Messages)
                .currentId(curMemberId)
                .build();

        return responseDtos;
    }

    @Transactional
    public List<ChatDto> findChatrooms(Member member) {
        if(member == null) {
            throw new HomealoneException(ErrorCode.MEMBER_NOT_FOUND);
        }

        //member는 현재 로그인한 사용자 즉 sender
        List<Chatting> chattings = chatRoomRepository.findAllChattingBySenderId(member.getId());
        List<ChatDto> chatDtoList = new ArrayList<>();
        for(Chatting chatting : chattings) {
            chatDtoList.add(chatting.toDto());
        }

        return chatDtoList;
    }

    @Transactional
    public void deleteChatroom(Member member, Long chatroomId) {
        //현재 로그인된 사용자 정보
        Member currentMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new HomealoneException(ErrorCode.MEMBER_NOT_FOUND));
        Long curMemberId = currentMember.getId();

        //삭제하려는 채팅방 정보
        Chatting chatting = chatRoomRepository.findById(chatroomId)
                .orElseThrow(() -> new HomealoneException(ErrorCode.CHATTING_ROOM_NOT_FOUND));
        Long senderId = chatting.getSender().getId();
        Long receiverId = chatting.getReceiver().getId();

        if(senderId != null && receiverId != null) {
            if(curMemberId == senderId) {
                currentMember.getChat_rooms().remove(chatting); //회원이 가진 채팅방 리스트에서 해당 채팅방 삭제
                chatting.getSender().setId(null);
            } else if(curMemberId == receiverId) {
                currentMember.getChat_rooms().remove(chatting); //회원이 가진 채팅방 리스트에서 해당 채팅방 삭제
                chatting.getReceiver().setId(null); //채팅방에서 해당 회원 id 삭제
            }
        } else { // 채팅방의 구성원 둘 중 한 명이라도 채팅방 나간 상태일 때
            if(curMemberId == senderId) {
                currentMember.getChat_rooms().remove(chatting); //회원이 가진 채팅방 리스트에서 해당 채팅방 삭제
                chatRoomRepository.delete(chatting); //채팅방 삭제하기(매핑된 메시지들도 cascade로 같이 삭제됨)
            } else if(curMemberId == receiverId) {
                currentMember.getChat_rooms().remove(chatting); //회원이 가진 채팅방 리스트에서 해당 채팅방 삭제
                chatRoomRepository.delete(chatting); //채팅방 삭제하기(매핑된 메시지들도 cascade로 같이 삭제됨)
            }
        }
    }
}
