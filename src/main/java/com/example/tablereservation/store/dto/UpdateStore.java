package com.example.tablereservation.store.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

public class UpdateStore {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class Request {
        private Long partnerId;
        private String storeName;
        private String storeAddress;
        private String storePhone;
        private String storeIntroduction;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
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
