package com.elice.homealone.global.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 모든 origin 허용
        configuration.setAllowedMethods(
            Arrays.asList("GET", "POST", "PATCH", "DELETE")); // 특정 HTTP 메소드 허용
        configuration.setAllowedHeaders(
            Arrays.asList("DNT", "User-Agent", "X-Requested-With", "If-Modified-Since",
                "Cache-Control", "Content-Type", "Range")); // 특정 헤더 허용
        configuration.setExposedHeaders(
            Arrays.asList("Content-Length", "Content-Range")); // 특정 헤더 노출 허용
        configuration.setMaxAge(1728000L); // preflight 캐시 시간
        configuration.setAllowCredentials(true); // credential 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 위의 CORS 설정 적용
        return source;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PATCH", "DELETE")
            .allowedHeaders("DNT", "User-Agent", "X-Requested-With", "If-Modified-Since",
                "Cache-Control", "Content-Type", "Range")
            .exposedHeaders("Content-Length", "Content-Range")
            .allowCredentials(true)
            .maxAge(1728000L);
    }
}
