package com.example.tablereservation.store.dto;

import com.example.tablereservation.store.entity.StoreEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
    private Long id;
    private Long partnerId;
    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String storeIntroduction;

    public static StoreDto fromEntity(StoreEntity storeEntity) {
        return StoreDto.builder()
                .id(storeEntity.getId())
                .partnerId(storeEntity.getPartner().getId())
                .storeName(storeEntity.getStoreName())
                .storeAddress(storeEntity.getStoreAddress())
                .storePhone(storeEntity.getStorePhone())
                .storeIntroduction(storeEntity.getStoreIntroduction())
                .build();
    }
}
