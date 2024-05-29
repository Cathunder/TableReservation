package com.example.tablereservation.user.dto;

import com.example.tablereservation.user.entity.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

public class RegisterUser {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
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
    @SuperBuilder
    public static class Response {
        private Long id;
        private String loginId;
        private String password;
        private String name;
        private String phone;
        private String role;

        public static Response fromDto(UserDto userDto) {
            return Response.builder()
                    .id(userDto.getId())
                    .loginId(userDto.getLoginId())
                    .password(userDto.getPassword())
                    .name(userDto.getName())
                    .phone(userDto.getPhone())
                    .role(userDto.getRole())
                    .build();
        }
    }
}
