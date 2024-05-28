package com.elice.homealone.global.exception;

import lombok.Getter;



@Getter
public class HomealoneException extends RuntimeException{
    private final ErrorCode errorCode;

    public HomealoneException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
