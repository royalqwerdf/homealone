package com.elice.homealone.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    //웹소켓 connect를 위한 End Point 설정 (/ws)
    //SockJS : 웹소켓이 지원하지 않는 브라우저에서도 실시간 통신을 지원할 수 있다
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //@Controller 클래스의 @MessageMapping을 적용한 메서드로 라우팅되도록 "/app" prefix 추가
        //연결된 브로커로 라우팅하기 위해 "/topic", "/queue" prefix 추가
        config.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/topic", "/queue");
    }

}
