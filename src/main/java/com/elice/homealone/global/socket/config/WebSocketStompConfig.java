package com.elice.homealone.global.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //웹소켓 활성화
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

        //서버에서 클라이언트로 발행하는 메세지에 대한 endpoint 설정 : 구독 중인 클라이언트에게 보낼 메시지
        config.enableSimpleBroker("/sub");

        //클라이언트에서 보낸 메시지에 대한 endpoint 설정
        config.setApplicationDestinationPrefixes("/pub");

    }

}
