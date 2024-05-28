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
    ID_ALREADY_EXIST("이미 사용중인 아이디입니다.", HttpStatus.CONFLICT.value()),
    ;

    private final String description;
    private final int statueCode;
}
