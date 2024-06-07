package com.example.tablereservation.store.entity;

import com.example.tablereservation.common.entity.BaseEntity;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.reservation.entity.ReservationEntity;
import com.example.tablereservation.review.entity.ReviewEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "store")
@SuperBuilder
public class StoreEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private PartnerEntity partner;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<ReservationEntity> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews = new ArrayList<>();

    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String storeIntroduction;
}
