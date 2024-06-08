package com.example.tablereservation.partner.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import lombok.*;

import java.time.LocalDateTime;

public class RegisterPartner {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String loginId;
        private String password;
        private String name;
        private String role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static PartnerEntity toEntity(Request request) {
            return PartnerEntity.builder()
                    .id(request.getId())
                    .loginId(request.getLoginId())
                    .password(request.getPassword())
                    .name(request.getName())
                    .role(request.getRole())
                    .createdAt(request.getCreatedAt())
                    .updatedAt(request.getUpdatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String loginId;
        private String password;
        private String name;
        private String role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response fromDto(PartnerDto partnerDto) {
            return Response.builder()
                    .id(partnerDto.getId())
                    .loginId(partnerDto.getLoginId())
                    .password(partnerDto.getPassword())
                    .name(partnerDto.getName())
                    .role(partnerDto.getRole())
                    .createdAt(partnerDto.getCreatedAt())
                    .updatedAt(partnerDto.getUpdatedAt())
                    .build();
        }
    }
}
