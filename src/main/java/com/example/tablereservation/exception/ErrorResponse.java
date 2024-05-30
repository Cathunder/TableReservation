package com.example.tablereservation.exception;

import lombok.*;

@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private int errorCode;
    private String message;
}
