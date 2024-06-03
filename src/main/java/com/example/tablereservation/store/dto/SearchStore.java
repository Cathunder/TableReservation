package com.example.tablereservation.store.dto;

import lombok.*;

public class SearchStore {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String searchContents;
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

        public static SearchStore.Response fromDto(StoreDto storeDto) {
            return SearchStore.Response.builder()
                    .storeName(storeDto.getStoreName())
                    .storeAddress(storeDto.getStoreAddress())
                    .storePhone(storeDto.getStorePhone())
                    .storeIntroduction(storeDto.getStoreIntroduction())
                    .build();
        }
    }
}
