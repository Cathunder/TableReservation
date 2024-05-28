package com.example.tablereservation.user.dto;

import com.example.tablereservation.partner.dto.RegisterPartner;
import com.example.tablereservation.user.entity.UserEntity;
import lombok.*;

public class RegisterUser {

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
        private String phone;
        private String role;

        public static UserEntity toEntity(Request request) {
            return UserEntity.builder()
                    .id(request.getId())
                    .loginId(request.getLoginId())
                    .password(request.getPassword())
                    .name(request.getName())
                    .phone(request.getPhone())
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
        private String phone;
        private String role;

        public static Response fromDto(UserDto partnerDto) {
            return Response.builder()
                    .id(partnerDto.getId())
                    .loginId(partnerDto.getLoginId())
                    .password(partnerDto.getPassword())
                    .name(partnerDto.getName())
                    .phone(partnerDto.getPhone())
                    .role(partnerDto.getRole())
                    .build();
        }
    }
}
