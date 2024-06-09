package com.example.tablereservation.partner.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.security.Authority;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

public class RegisterPartnerDto {

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

        public static PartnerEntity toEntity(Request request) {
            return PartnerEntity.builder()
                    .loginId(request.getLoginId())
                    .password(request.getPassword())
                    .name(request.getName())
                    .role(Authority.ROLE_PARTNER.name())
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

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
