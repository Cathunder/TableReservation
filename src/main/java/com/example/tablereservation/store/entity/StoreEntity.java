package com.example.tablereservation.store.entity;

import com.example.tablereservation.common.entity.BaseEntity;
import com.example.tablereservation.partner.entity.PartnerEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "store")
@SuperBuilder
public class StoreEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private PartnerEntity partner;

    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String storeIntroduction;
}
