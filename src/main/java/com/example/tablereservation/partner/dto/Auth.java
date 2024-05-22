package com.example.tablereservation.partner.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import lombok.Data;

public class Auth {

    @Data
    public static class register {
        private String loginId;
        private String password;
        private String role;

        public PartnerEntity toEntity() {
            return PartnerEntity.builder()
                    .loginId(loginId)
                    .password(password)
                    .role(role)
                    .build();
        }
    }

    @Data
    public static class login {
        private String loginId;
        private String password;
    }
}
