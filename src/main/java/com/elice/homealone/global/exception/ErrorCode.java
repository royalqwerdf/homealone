package com.elice.homealone.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //400
    EMAIL_NOT_FOUND(NOT_FOUND, "존재하지 않는 이메일입니다"),
    MEMBER_NOT_FOUND(NOT_FOUND,"존재하지 않는 회원입니다. "),
    ROOM_NOT_FOUND(NOT_FOUND, "존재하지 않는 게시물입니다"),
    TALK_NOT_FOUND(NOT_FOUND, "존재하지 않는 게시물입니다"),
    RECIPE_NOT_FOUND(NOT_FOUND, "존재하지 않는 레시피입니다"),
    NOT_UNAUTHORIZED_ACTION(FORBIDDEN,"작성자와 일치하지 않습니다."),
    NO_JWT_TOKEN(FORBIDDEN,"JWT토큰이 비어있습니다."),


    //401
    MISMATCHED_PASSWORD(UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않는 토큰입니다.");

    //402

    //500


    private final HttpStatus httpStatus;
    private final String message;
}
