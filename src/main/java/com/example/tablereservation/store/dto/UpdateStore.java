package com.example.tablereservation.store.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.store.entity.StoreEntity;
import lombok.*;

import java.time.LocalDateTime;

public class UpdateStore {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long partnerId;
        private String storeName;
        private String storeAddress;
        private String storePhone;
        private String storeIntroduction;

//        public static StoreEntity toEntity(UpdateStore.Request request, PartnerEntity partnerEntity) {
//            return StoreEntity.builder()
//                    .partner(partnerEntity)
//                    .storeName(request.getStoreName())
//                    .storeAddress(request.getStoreAddress())
//                    .storePhone(request.getStorePhone())
//                    .storeIntroduction(request.getStoreIntroduction())
//                    .build();
//        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String storeName;
        private String storeAddress;
        private String storePhone;
        private String storeIntroduction;
        private LocalDateTime updateDate;

        public static Response fromDto(StoreDto partnerDto) {
            return Response.builder()
                    .storeName(partnerDto.getStoreName())
                    .storeAddress(partnerDto.getStoreAddress())
                    .storePhone(partnerDto.getStorePhone())
                    .storeIntroduction(partnerDto.getStoreIntroduction())
                    .updateDate(partnerDto.getUpdateDate())
                    .build();
        }
    }
}
