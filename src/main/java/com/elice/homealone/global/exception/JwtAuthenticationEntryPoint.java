package com.elice.homealone.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ResponseEntity<Response.ErrorResponse> responseEntity = globalExceptionHandler.handleAuthenticationException(authException, null);

        response.setStatus(responseEntity.getStatusCodeValue());
        response.setContentType("application/json");
        response.getWriter().write("{ \"status\": " + responseEntity.getStatusCodeValue() + ", \"error\": \"" + responseEntity.getBody().getError() + "\", \"message\": \"" + responseEntity.getBody().getMessage() + "\" }");
    }
}
