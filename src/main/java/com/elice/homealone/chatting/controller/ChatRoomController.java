package com.elice.homealone.chatting.controller;

import com.elice.homealone.chatting.entity.ChatDto;
import com.elice.homealone.chatting.entity.ChatMessage;
import com.elice.homealone.chatting.entity.Chatting;
import com.elice.homealone.chatting.repository.ChatRoomRepository;
import com.elice.homealone.chatting.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;

    //회원의 모든 채팅방 목록 반환
    @GetMapping("/chatting/{memberId}")
    public List<Chatting> chattingRooms(@PathVariable Long memberId) {
        List<Chatting> chattingList = chatRoomRepository.findAllChattingBySenderId(memberId);
        return chattingList;
    }

    //선택 채팅방 조회
    @GetMapping("/chatting/{chatroomId}")
    public Map<String, Object> chatroomInfo(@PathVariable Long chatroomId) {
        Chatting chatting = chatRoomRepository.findChattingById(chatroomId);

        List<ChatMessage> senderChatList = chatRoomService.findChatList(chatroomId, chatting.getSender().getId());
        List<ChatMessage> receiverChatList = chatRoomService.findChatList(chatroomId, chatting.getReceiver().getId());
        Map<String, Object> result = new HashMap<>();
        result.put("senderMessage", senderChatList);
        result.put("receiverMessage", receiverChatList);

        return result;
    }

    //채팅방 생성
    @PostMapping("/chatting")
    public ResponseEntity<ChatDto> makeChat(@RequestBody ChatDto chatDto) {
        Chatting createdRoom = chatRoomService.makeChat(chatDto);

        return ResponseEntity.ok().body(createdRoom.toDto());
    }
}
