package com.example.tablereservation.reservation.dto;

import com.example.tablereservation.common.type.ReservationStatus;
import com.example.tablereservation.reservation.entity.ReservationEntity;
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
    private String userPhone;
    private Integer people;
    private ReservationStatus status;
    private LocalDateTime reservationDateTime;
    private LocalDateTime createdAt;

    public static ReservationDto fromEntity(ReservationEntity reservationEntity) {
        return ReservationDto.builder()
                .id(reservationEntity.getId())
                .storeId(reservationEntity.getStore().getId())
                .storeName(reservationEntity.getStore().getStoreName())
                .username(reservationEntity.getUser().getName())
                .userPhone(reservationEntity.getUser().getPhone())
                .people(reservationEntity.getPeople())
                .status(reservationEntity.getStatus())
                .reservationDateTime(reservationEntity.getReservationDateTime())
                .createdAt(reservationEntity.getCreatedDate())
                .build();
    }
}
