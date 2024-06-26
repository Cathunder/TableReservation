package com.example.tablereservation.reservation.entity;

import com.example.tablereservation.common.entity.BaseEntity;
import com.example.tablereservation.common.type.ReservationStatus;
import com.example.tablereservation.review.entity.ReviewEntity;
import com.example.tablereservation.store.entity.StoreEntity;
import com.example.tablereservation.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reservation")
@SuperBuilder
public class ReservationEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY)
    private ReviewEntity review;

    private Integer people;
    private ReservationStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH")
    private LocalDateTime reservationAt;
}
