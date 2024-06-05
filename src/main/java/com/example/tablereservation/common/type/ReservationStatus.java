package com.example.tablereservation.common.type;

public enum ReservationStatus {
    REQUEST,    // 예약요청
    CANCEL,     // 예약취소

    APPROVE,    // 예약승인
    REFUSE,     // 예약거절

    ARRIVED,    // 도착확인
    COMPLETE    // 이용완료
}
