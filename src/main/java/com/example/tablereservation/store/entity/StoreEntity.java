package com.example.tablereservation.store.entity;

import com.example.tablereservation.partner.entity.PartnerEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "store")
@Builder
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private PartnerEntity partner;

    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String storeIntroduction;
}
