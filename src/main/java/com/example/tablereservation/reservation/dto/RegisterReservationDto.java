package com.example.tablereservation.reservation.dto;

import com.example.tablereservation.common.type.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

        @NotNull(message = "상점 id를 입력해주세요.")
        private Long storeId;

        @NotNull(message = "예약인원을 입력해주세요.")
        @Min(0)
        private Integer people;

        @NotNull(message = "예약 날짜를 입력해주세요.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate date;

        @NotNull(message = "예약 시간을 입력해주세요.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH")
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
        private String userPhone;
        private Integer people;
        private ReservationStatus status;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH")
        private LocalDateTime reservationDateTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;

        public static Response fromDto(ReservationDto reservationDto) {
            return Response.builder()
                    .storeId(reservationDto.getStoreId())
                    .storeName(reservationDto.getStoreName())
                    .username(reservationDto.getUsername())
                    .userPhone(reservationDto.getUserPhone())
                    .people(reservationDto.getPeople())
                    .status(reservationDto.getStatus())
                    .reservationDateTime(reservationDto.getReservationDateTime())
                    .createdAt((reservationDto.getCreatedAt()))
                    .build();
        }
    }
}
