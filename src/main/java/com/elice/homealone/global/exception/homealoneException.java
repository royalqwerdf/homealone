package com.elice.homealone.global.exception;

import lombok.Getter;

@Getter
public class homealoneException extends RuntimeException {
    private final ErrorCode errorCode;

    public homealoneException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
