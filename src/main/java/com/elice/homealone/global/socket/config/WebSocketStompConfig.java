package com.elice.homealone.global.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //웹소켓 서버 활성화
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    //웹소켓 connect를 위한 End Point 설정 "/ws"
    //SockJS : 웹소켓이 지원하지 않는 브라우저에서도 실시간 통신을 지원할 수 있다
    //SockJS 쓸 땐 클라이언트에서 웹소켓 요청을 보낼 때 설정한 endpoint 뒤에 "/webSocket"을 추가해야함
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") //모든 출처에대한 Cors 설정
                .withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // "/sub"로 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의
        config.enableSimpleBroker("/topic");

        // "/pub"로 시작되는 메시지가 message-handling methods로 라우팅 되어야 함을 명시
        config.setApplicationDestinationPrefixes("/app");

    }

}
