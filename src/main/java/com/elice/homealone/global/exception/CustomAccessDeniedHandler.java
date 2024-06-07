package com.elice.homealone.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ResponseEntity<Response.ErrorResponse> responseEntity = globalExceptionHandler.handleAccessDeniedException(accessDeniedException, null);

        response.setStatus(responseEntity.getStatusCodeValue());
        response.setContentType("application/json");
        response.getWriter().write("{ \"status\": " + responseEntity.getStatusCodeValue() + ", \"error\": \"" + responseEntity.getBody().getError() + "\", \"message\": \"" + responseEntity.getBody().getMessage() + "\" }");
    }
}
