//package com.elice.homealone.socket;
//
//import com.elice.homealone.global.socket.handler.WebSocketHandlerImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.client.WebSocketClient;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//
//import java.net.URI;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class WebSocketTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Test
//    public void testWebSocket() throws Exception {
//        WebSocketClient client = new StandardWebSocketClient();
//        //execute 메서드로 웹 소켓 연결을 시도, 연결 성공하면 WebSocket 세션을 반환
//        WebSocketSession session = client.execute(new WebSocketHandlerImpl(), null,
//                new URI("ws://localhost:" + port + "/websocket-endpoint")).get();
//
//        //서버로의 메시지 전송 확인
//        session.sendMessage(new TextMessage("Hello, Server"));
//        Thread.sleep(1000); //서버 응답을 위한 대기
//
//        session.close();
//    }
//}
