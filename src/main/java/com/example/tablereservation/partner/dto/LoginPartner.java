package com.example.tablereservation.partner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginPartner {

    @NotBlank(message = "로그인 아이디를 입력하세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
