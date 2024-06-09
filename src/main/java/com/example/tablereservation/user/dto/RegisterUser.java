package com.example.tablereservation.user.dto;

import com.example.tablereservation.security.Authority;
import com.example.tablereservation.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

public class RegisterUser {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "로그인 아이디를 입력하세요.")
        private String loginId;

        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;

        @NotBlank(message = "이름을 입력하세요.")
        private String name;

        @NotBlank(message = "휴대폰 번호를 입력하세요.")
        @Size(min = 11, max = 11)
        private String phone;

        public static UserEntity toEntity(Request request) {
            return UserEntity.builder()
                    .loginId(request.getLoginId())
                    .password(request.getPassword())
                    .name(request.getName())
                    .phone(request.getPhone())
                    .role(Authority.ROLE_USER.name())
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

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt;

        public static Response fromDto(UserDto userDto) {
            return Response.builder()
                    .id(userDto.getId())
                    .loginId(userDto.getLoginId())
                    .password(userDto.getPassword())
                    .name(userDto.getName())
                    .phone(userDto.getPhone())
                    .role(userDto.getRole())
                    .createdAt(userDto.getCreatedAt())
                    .updatedAt(userDto.getUpdatedAt())
                    .build();
        }
    }
}
