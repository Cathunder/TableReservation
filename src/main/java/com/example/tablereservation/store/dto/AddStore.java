package com.example.tablereservation.store.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.store.entity.StoreEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
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

//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createDate;
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updateDate;

        public static Response fromDto(StoreDto partnerDto) {
            return Response.builder()
                    .partnerId(partnerDto.getPartnerId())
                    .storeName(partnerDto.getStoreName())
                    .storeAddress(partnerDto.getStoreAddress())
                    .storePhone(partnerDto.getStorePhone())
                    .storeIntroduction(partnerDto.getStoreIntroduction())
                    .createDate(partnerDto.getCreateDate())
                    .updateDate(partnerDto.getUpdateDate())
                    .build();
        }
    }
}