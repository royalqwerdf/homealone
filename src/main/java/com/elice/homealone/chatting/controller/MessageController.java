package com.elice.homealone.chatting.controller;

import com.elice.homealone.chatting.entity.ChatMessage;
import com.elice.homealone.chatting.service.ChatRoomService;
import com.elice.homealone.chatting.model.MessageModel;
import com.elice.homealone.chatting.model.OutputMessageModel;
import com.elice.homealone.chatting.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
    private final MessageService messageService;

    @MessageMapping("/chat-sendMessage/{chatroomId}")
    @SendTo("/topic/public")
    public MessageModel sendMessage(@Payload MessageModel messageModel, @DestinationVariable Long chatroomId) {
        String content = messageModel.getContent();
        String chatType = messageModel.getType().toString();
        final String time = new SimpleDateFormat("HH:mm").format(new Date());

        messageService.saveMessage(chatroomId, content, time, chatType);

        return messageModel;
    }

    /*
    @MessageMapping("/chat-addUser")
    @SendTo("/topic/public")
    public MessageModel addUser(@Payload MessageModel messageModel, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("usernme", messageModel.getSender());
        return messageModel;
    }
    */
}
