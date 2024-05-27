package com.elice.homealone.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //400
    EMAIL_NOT_FOUND(NOT_FOUND, "존재하지 않는 이메일입니다"),
    RECIPE_NOT_FOUND(NOT_FOUND, "존재하지 않는 레시피입니다"),
    //401
    MISMATCHED_PASSWORD(UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않는 토큰입니다.");

    //402

    //500


    private final HttpStatus httpStatus;
    private final String message;
}
