package com.elice.homealone.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class Response {
    @Getter
    @Setter
    public static class ApiResponse{
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private final int status;
        private final String error;
        private final String message;
    }
}
