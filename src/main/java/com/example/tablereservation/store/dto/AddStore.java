package com.example.tablereservation.store.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.store.entity.StoreEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

public class AddStore {

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
    @SuperBuilder
    public static class Response {
        private Long partnerId;
        private String storeName;
        private String storeAddress;
        private String storePhone;
        private String storeIntroduction;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedDate;

        public static Response fromDto(StoreDto storeDto) {
            return Response.builder()
                    .partnerId(storeDto.getPartnerId())
                    .storeName(storeDto.getStoreName())
                    .storeAddress(storeDto.getStoreAddress())
                    .storePhone(storeDto.getStorePhone())
                    .storeIntroduction(storeDto.getStoreIntroduction())
                    .createdDate(storeDto.getCreateDate())
                    .updatedDate(storeDto.getUpdateDate())
                    .build();
        }
    }
}
