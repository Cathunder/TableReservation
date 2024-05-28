package com.example.tablereservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    protected ResponseEntity<ErrorResponse> handleReservationException(ReservationException e) {
        ErrorResponse errorResponse =
                ErrorResponse.builder()
                        .errorCode(e.getErrorCode())
                        .message(e.getMessage())
                        .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getErrorCode()));
    }
}