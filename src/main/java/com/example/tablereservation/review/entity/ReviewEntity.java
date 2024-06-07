package com.example.tablereservation.review.entity;

import com.example.tablereservation.common.entity.BaseEntity;
import com.example.tablereservation.reservation.entity.ReservationEntity;
import com.example.tablereservation.store.entity.StoreEntity;
import com.example.tablereservation.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "review")
@SuperBuilder
public class ReviewEntity extends BaseEntity {

    private String contents;
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;
}
