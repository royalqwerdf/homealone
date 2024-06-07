package com.elice.homealone.global.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("나홀로집에 프로젝트")
                        .version("1.0")
                        .description("엘리스 클라우드 트랙 2기 7팀의 최종 프로젝트입니다."));
    }
}