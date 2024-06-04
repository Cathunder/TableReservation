package com.example.tablereservation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.tablereservation.exception.ErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    protected ResponseEntity<ErrorResponse> handleReservationException(ReservationException e) {
        log.error("ReservationException is occurred", e);

        ErrorResponse errorResponse =
                ErrorResponse.builder()
                        .errorCode(e.getErrorCode())
                        .message(e.getMessage())
                        .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException is occurred", e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(INVALID_REQUEST.getStatueCode())
                .message(INVALID_REQUEST.getDescription())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getErrorCode()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException is occurred", e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(INVALID_REQUEST.getStatueCode())
                .message(INVALID_REQUEST.getDescription())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception is occurred", e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(INVALID_REQUEST.getStatueCode())
                .message(INVALID_REQUEST.getDescription())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getErrorCode()));
    }
}