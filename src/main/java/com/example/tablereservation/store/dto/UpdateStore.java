package com.example.tablereservation.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

public class UpdateStore {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String storeName;
        private String storeAddress;

        @Min(9)
        @Max(12)
        private String storePhone;

        private String storeIntroduction;
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

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updateDate;

        public static Response fromDto(StoreDto storeDto) {
            return Response.builder()
                    .storeName(storeDto.getStoreName())
                    .storeAddress(storeDto.getStoreAddress())
                    .storePhone(storeDto.getStorePhone())
                    .storeIntroduction(storeDto.getStoreIntroduction())
                    .createDate(storeDto.getCreateDate())
                    .updateDate(storeDto.getUpdateDate())
                    .build();
        }
    }
}
