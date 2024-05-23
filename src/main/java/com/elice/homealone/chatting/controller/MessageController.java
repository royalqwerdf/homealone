package com.elice.homealone.chatting.controller;

import com.elice.homealone.socket.model.MessageModel;
import com.elice.homealone.socket.model.OutputMessageModel;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController {

    /*메시지 모델
    *   let chatMessage = {
    *       sender: sessionStorage.getItem("username"),
    *       content: messageInput.value,
    *       chatUuid: chatUuid,
    *       type: 'CHAT'
    *   };
    */


    @MessageMapping("/message/send/{chatUuid}") //"/pub/message/send/"+chatUuid에 라우팅
    @SendTo("/sub/chat/{chatUuid}")
    public OutputMessageModel sendMessage(@Payload MessageModel messageModel,
                            //경로로부터 chatUuid 추출
                            @DestinationVariable Long chatUuid) {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());

        //서버에서 메시지를 가공해 OutputMessageModel에 담고 리턴하면 메시지 브로커가 받음
        return new OutputMessageModel(messageModel.getSender(), messageModel.getChatUuid(), messageModel.getContent(),
                time, OutputMessageModel.MessageType.CHAT);
    }
}
