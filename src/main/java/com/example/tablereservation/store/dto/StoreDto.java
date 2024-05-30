package com.example.tablereservation.store.dto;

import com.example.tablereservation.store.entity.StoreEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoreDto {
    private Long id;
    private Long partnerId;
    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String storeIntroduction;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    public static StoreDto fromEntity(StoreEntity storeEntity) {
        return StoreDto.builder()
                .id(storeEntity.getId())
                .partnerId(storeEntity.getPartner().getId())
                .storeName(storeEntity.getStoreName())
                .storeAddress(storeEntity.getStoreAddress())
                .storePhone(storeEntity.getStorePhone())
                .storeIntroduction(storeEntity.getStoreIntroduction())
                .createDate(storeEntity.getCreatedDate())
                .updateDate(storeEntity.getUpdatedDate())
                .build();
    }
}
