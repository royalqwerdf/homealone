package com.elice.homealone.chatting.controller;

import com.elice.homealone.chatting.entity.ChatDto;
import com.elice.homealone.chatting.entity.ChatMessage;
import com.elice.homealone.chatting.entity.Chatting;
import com.elice.homealone.chatting.repository.ChatRoomRepository;
import com.elice.homealone.chatting.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;


    //회원의 모든 채팅방 목록 반환
    @GetMapping("/chattings")
    public ResponseEntity<List<ChatDto>> chattingRooms(@RequestHeader("Authorization") String token) {
        String accessToken = token.substring(7);
        log.info("{} : not exist token", accessToken);
        List<ChatDto> chatrooms = chatRoomService.findChatrooms(accessToken);

        return ResponseEntity.ok().body(chatrooms);

    }

    //선택 채팅방 조회
    @GetMapping("/chatting/{chatroomId}")
    public ResponseEntity<Map<String, Object>> chatroomInfo(@PathVariable Long chatroomId) {

        Map<String, Object> chatMessages = chatRoomService.findChatList(chatroomId);

        return ResponseEntity.ok().body(chatMessages);
    }

    //채팅방 생성
    @PostMapping("/chatting")
    public ResponseEntity<ChatDto> makeChat(@RequestHeader("Authorization") String token, @RequestBody ChatDto chatDto) {
        ChatDto createdRoom = chatRoomService.makeChat(token, chatDto);

        return ResponseEntity.ok().body(createdRoom);
    }
}
