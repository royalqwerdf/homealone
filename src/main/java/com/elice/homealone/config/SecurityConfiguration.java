package com.elice.homealone.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        // csrf, http basic 비활성화
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.formLogin(AbstractHttpConfigurer::disable);
//        http.httpBasic(AbstractHttpConfigurer::disable);
//
//        // 세션 사용 안함
//        http.setSharedObject(SessionManagementConfigurer.class, new SessionManagementConfigurer<HttpSecurity>().sessionCreationPolicy(
//            SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }

    // h2 콘솔에 대한 요청이 스프링 시큐리티 필터를 통과 하지 않도록 하는 설정
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
            .requestMatchers(PathRequest.toH2Console());
    }
}
