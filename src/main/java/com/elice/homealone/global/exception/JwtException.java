package com.elice.homealone.global.exception;

public class JwtException extends HomealoneException{
    public JwtException() {
        super(ErrorCode.JWT_EXCEPTION);
    }
}
