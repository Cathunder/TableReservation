package com.example.tablereservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 토큰
    TOKEN_WAS_EXPIRED("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED.value()),

    // 파트너
    PARTNER_NOT_FOUND("해당 파트너가 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()),

    // 유저
    USER_NOT_FOUND("해당 유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()),

    // 공용
    UNAUTHORIZED("권한이 없습니다.", HttpStatus.FORBIDDEN.value()),
    ID_ALREADY_EXIST("이미 사용중인 아이디입니다.", HttpStatus.CONFLICT.value()),
    ID_NOT_EXIST("존재하지 않는 아이디입니다.", HttpStatus.UNAUTHORIZED.value()),
    PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value()),
    INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST.value()),

    // 매장
    STORE_NAME_ALREADY_EXIST("이미 등록된 매장명입니다.", HttpStatus.CONFLICT.value()),
    STORE_NOT_EXIST("존재하지 않는 매장입니다.", HttpStatus.BAD_REQUEST.value()),

    // 예약
    RESERVATION_ALREADY_EXIST("해당 시간대에 이미 예약이 존재합니다. 다른 시간대를 선택해주세요.", HttpStatus.CONFLICT.value()),
    RESERVATION_NOT_EXIST("예약이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED.value()),
    CANNOT_RESERVE_BEFORE_NOW("지난시간대의 예약은 불가능합니다.", HttpStatus.BAD_REQUEST.value()),
    CANNOT_RESERVE_BEFORE_1HOUR("이용 1시간 전에는 예약을 할 수 없습니다.", HttpStatus.BAD_REQUEST.value()),
    RESERVATION_STATUS_NOT_APPROVE("승인된 예약건에 한해 도착완료 처리를 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()),
    CAN_ARRIVED_CONFIRM_BEFORE_10MIN("예약시간 10분전부터 도착확인이 가능합니다.", HttpStatus.BAD_REQUEST.value()),
    RESERVATION_STATUS_NOT_ARRIVED("도착이 확인된 예약건만 완료처리를 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()),
    CAN_COMPLETE_AFTER_RESERVATION("예약시간 이후부터 완료처리가 가능합니다.", HttpStatus.BAD_REQUEST.value()),

    // 리뷰
    RESERVATION_STATUS_NOT_COMPLETE("이용완료된 예약건에 대해서만 리뷰를 작성할 수 있습니다.", HttpStatus.BAD_REQUEST.value()),
    REVIEW_ALREADY_EXIST("이미 리뷰를 작성했습니다. 리뷰수정만 가능합니다.", HttpStatus.CONFLICT.value()),
    REVIEW_NOT_EXIST("리뷰가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED.value()),
    ;

    private final String description;
    private final int statueCode;
}
