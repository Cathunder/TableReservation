package com.example.tablereservation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservationException extends RuntimeException {
    private int errorCode;
    private String message;

    public ReservationException(ErrorCode errorCode) {
        this.errorCode = errorCode.getStatueCode();
        this.message = errorCode.getDescription();
    }
}
