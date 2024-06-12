package com.elice.homealone.global.socket.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandlerImpl implements WebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    //connection이 성립된 이후 작동되는 메서드(유저가 채팅방에 입장했을 때)
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String chatroomId = getChatRoomId(session);
        log.info("now chatting room number : {}", chatroomId);

    }

    //양방향 통신 로직
    //session은 전송자(sender), 데이터는 message
    //전달받은 세션을 통해 전송자의 메시지를 확인
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if(message instanceof TextMessage) {
            String payload = ((TextMessage) message).getPayload();
            log.info("payload {}", payload);
        }
    }

    //통신 에러 발생시
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("WebSocket error {}", exception.getMessage());
    }

    //connection이 종료되었을 때(유저가 채팅방을 나갔을 때)
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("WebSocket connect close");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    //웹소켓이 연결된 채팅방의 id를 반환할 메소드
    private String getChatRoomId(WebSocketSession session) {
        // URI에서 쿼리 파라미터 추출
        URI uri = session.getUri();
        if (uri != null) {
            String query = uri.getQuery();
            if (query != null) {
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    if (pair.length > 1 && "chatRoomId".equals(pair[0])) {
                        return pair[1];
                    }
                }
            }
        }
        return "default"; // 기본 채팅방 ID
    }
}
