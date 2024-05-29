package com.example.tablereservation.store.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.store.entity.StoreEntity;
import lombok.*;

public class AddStore {

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

        public static StoreEntity toEntity(Request request, PartnerEntity partnerEntity) {
            return StoreEntity.builder()
                    .partner(partnerEntity)
                    .storeName(request.getStoreName())
                    .storeAddress(request.getStoreAddress())
                    .storePhone(request.getStorePhone())
                    .storeIntroduction(request.getStoreIntroduction())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long partnerId;
        private String storeName;
        private String storeAddress;
        private String storePhone;
        private String storeIntroduction;

        public static Response fromDto(StoreDto partnerDto) {
            return Response.builder()
                    .partnerId(partnerDto.getPartnerId())
                    .storeName(partnerDto.getStoreName())
                    .storeAddress(partnerDto.getStoreAddress())
                    .storePhone(partnerDto.getStorePhone())
                    .storeIntroduction(partnerDto.getStoreIntroduction())
                    .build();
        }
    }
}
