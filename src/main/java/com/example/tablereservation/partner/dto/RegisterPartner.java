package com.example.tablereservation.partner.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import lombok.*;

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

        public static PartnerEntity toEntity(Request request) {
            return PartnerEntity.builder()
                    .id(request.getId())
                    .loginId(request.getLoginId())
                    .password(request.getPassword())
                    .name(request.getName())
                    .role(request.getRole())
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

        public static Response fromDto(PartnerDto partnerDto) {
            return Response.builder()
                    .id(partnerDto.getId())
                    .loginId(partnerDto.getLoginId())
                    .password(partnerDto.getPassword())
                    .name(partnerDto.getName())
                    .role(partnerDto.getRole())
                    .build();
        }
    }
}
