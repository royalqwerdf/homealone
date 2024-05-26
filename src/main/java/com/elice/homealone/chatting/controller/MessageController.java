package com.elice.homealone.chatting.controller;

import com.elice.homealone.chatting.entity.ChatMessage;
import com.elice.homealone.chatting.service.ChatRoomService;
import com.elice.homealone.global.socket.model.MessageModel;
import com.elice.homealone.global.socket.model.OutputMessageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Controller
public class MessageController {

    /* 메시지 모델
    *   let message = {
    *       senderId: sessionStorage.getId(),
    *       content: messageInput.value,
    *       type: 'CHAT'
    *   };
    */

    private final ChatRoomService chatRoomService;

    @MessageMapping("/{chatroomId}") //"/pub/"+chatroomId에 라우팅
    @SendTo("/chatroom/{chatroomId}")
    public OutputMessageModel sendMessage(@DestinationVariable Long chatroomId, MessageModel message) {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());

        ChatMessage chatMessage = chatRoomService.saveMessage(chatroomId, message.getSenderId(), message.getContent(), time);

        //서버에서 메시지를 가공해 OutputMessageModel에 담고 리턴하면 메시지 브로커가 받음
        return new OutputMessageModel(chatMessage.getChatting().getId(), chatMessage.getMember().getId(), chatMessage.getMember().getName(),
                chatMessage.getContent(), time, OutputMessageModel.MessageType.CHAT);
    }


}
