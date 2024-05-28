package com.example.tablereservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 파트너
    PARTNER_NOT_FOUND("해당 파트너를 찾지 못했습니다.", HttpStatus.BAD_REQUEST.value()),

    // 공용
    USER_NOT_FOUND("해당 유저를 찾지 못했습니다.", HttpStatus.BAD_REQUEST.value()),
    ID_ALREADY_EXIST("이미 사용중인 아이디입니다.", HttpStatus.CONFLICT.value()),
    ID_NOT_EXIST("존재하지 않는 아이디입니다.", HttpStatus.UNAUTHORIZED.value()),
    PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value()),



    ;

    private final String description;
    private final int statueCode;
}
