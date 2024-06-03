package com.example.tablereservation.reservation.dto;

import com.example.tablereservation.common.type.ReservationStatus;
import com.example.tablereservation.reservation.entity.ReservationEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private Long id;
    private Long storeId;
    private String storeName;
    private String username;
    private Integer people;
    private ReservationStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationDate;

    public static ReservationDto fromEntity(ReservationEntity reservationEntity) {
        return ReservationDto.builder()
                .id(reservationEntity.getId())
                .storeId(reservationEntity.getStore().getId())
                .storeName(reservationEntity.getStore().getStoreName())
                .username(reservationEntity.getUsername())
                .people(reservationEntity.getPeople())
                .status(reservationEntity.getStatus())
                .reservationDate(reservationEntity.getReservationDate())
                .build();
    }
}
