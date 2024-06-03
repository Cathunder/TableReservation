package com.example.tablereservation.reservation.dto;

import com.example.tablereservation.common.type.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RegisterReservationDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long storeId;

        private String loginId;
        private Integer people;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate date;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime time;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long storeId;
        private String storeName;
        private String username;
        private Integer people;
        private ReservationStatus status;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime reservationDate;

        public static Response fromDto(ReservationDto reservationDto) {
            return Response.builder()
                    .storeId(reservationDto.getStoreId())
                    .storeName(reservationDto.getStoreName())
                    .username(reservationDto.getUsername())
                    .people(reservationDto.getPeople())
                    .status(reservationDto.getStatus())
                    .reservationDate(reservationDto.getReservationDate())
                    .build();
        }
    }
}
