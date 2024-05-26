package com.example.tablereservation.partner.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import lombok.Data;

public class Auth {

    @Data
    public static class register {
        private String loginId;
        private String password;
        private String name;
        private String role;

        public PartnerEntity toEntity() {
            return PartnerEntity.builder()
                    .loginId(this.loginId)
                    .password(this.password)
                    .name(this.name)
                    .role(this.role)
                    .build();
        }
    }

    @Data
    public static class login {
        private String loginId;
        private String password;
    }
}
