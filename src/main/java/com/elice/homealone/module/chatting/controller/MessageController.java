package com.elice.homealone.module.chatting.controller;

import com.elice.homealone.module.chatting.service.ChatRoomService;
import com.elice.homealone.module.chatting.model.MessageModel;
import com.elice.homealone.module.chatting.model.OutputMessageModel;
import com.elice.homealone.module.chatting.service.MessageService;
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
    private final MessageService messageService;

    @MessageMapping("/chat-sendMessage/{chatroomId}")
    @SendTo("/topic/public/{chatroomId}")
    public OutputMessageModel sendMessage(@Payload MessageModel messageModel, @DestinationVariable Long chatroomId) {
        String content = messageModel.getContent();
        String chatType = messageModel.getType().toString();
        Date now = new Date();
        final String time = new SimpleDateFormat("HH:mm").format(now);

        messageService.saveMessage(chatroomId, content, now, time, chatType);

        return new OutputMessageModel(content, time);
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
