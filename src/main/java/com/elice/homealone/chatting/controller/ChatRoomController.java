package com.elice.homealone.chatting.controller;

import com.elice.homealone.chatting.entity.ChatDto;
import com.elice.homealone.chatting.entity.Chatting;
import com.elice.homealone.chatting.repository.ChatRoomRepository;
import com.elice.homealone.chatting.service.ChatRoomService;
import com.elice.homealone.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatting")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;

    //회원의 모든 채팅방 목록 반환
    @GetMapping("/chatroom/{member_id}")
    public List<Chatting> chattingRooms(@PathVariable Long memberId) {
        List<Chatting> chattingList = chatRoomRepository.findAllChattingBySenderId(memberId);
        return chattingList;
    }

    //선택 채팅방 조회
    @GetMapping("/chatroom/{chatUuid}")
    public Chatting chatroomInfo(@PathVariable String chatUuid) {
        return chatRoomRepository.findChattingByChatUuid(chatUuid);
    }

    //채팅방 생성
    @PostMapping("/chatroom")
    public ResponseEntity<Chatting> makeChat(@RequestBody ChatDto chatDto) {
        Chatting createdRoom = chatRoomService.makeChat(chatDto);

        return ResponseEntity.ok().body(createdRoom);
    }
}
