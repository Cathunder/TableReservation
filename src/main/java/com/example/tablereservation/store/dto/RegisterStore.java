package com.example.tablereservation.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

public class RegisterStore {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotBlank(message = "매장명을 입력해주세요.")
        private String storeName;

        @NotBlank(message = "매장주소를 입력해주세요.")
        private String storeAddress;

        @NotNull(message = "매장 전화번호를 입력해주세요.")
        @Size(min = 9, max = 12)
        private String storePhone;

        private String storeIntroduction;
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