package com.example.tablereservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 토큰
    TOKEN_WAS_EXPIRED("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED.value()),
    UNKNOWN_ROLE("존재하지 않는 권한입니다.", HttpStatus.BAD_REQUEST.value()),

    // 파트너
    PARTNER_NOT_FOUND("해당 파트너를 찾지 못했습니다.", HttpStatus.BAD_REQUEST.value()),

    // 공용
    USER_NOT_FOUND("해당 유저를 찾지 못했습니다.", HttpStatus.BAD_REQUEST.value()),
    ID_ALREADY_EXIST("이미 사용중인 아이디입니다.", HttpStatus.CONFLICT.value()),
    ID_NOT_EXIST("존재하지 않는 아이디입니다.", HttpStatus.UNAUTHORIZED.value()),
    PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value()),

    // 상점
    PARTNER_NOT_MATCHED("해당 상점에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN.value()),
    STORE_ALREADY_EXIST("이미 등록된 상점입니다.", HttpStatus.CONFLICT.value()),
    STORE_NOT_EXIST("존재하지 않는 상점입니다.", HttpStatus.BAD_REQUEST.value()),
    NOT_PARTNER("파트너가 아닙니다.", HttpStatus.UNAUTHORIZED.value()),

    // 예약
    USER_INCORRECT("유저정보가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value()),
    ;

    private final String description;
    private final int statueCode;
}
