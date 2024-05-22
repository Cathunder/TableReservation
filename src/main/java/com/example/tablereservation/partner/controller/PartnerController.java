package com.example.tablereservation.partner.controller;

import com.example.tablereservation.partner.dto.Auth;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.partner.service.PartnerService;
import com.example.tablereservation.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class PartnerController {

    private final TokenProvider tokenProvider;
    private final PartnerService partnerService;

    /**
     * 파트너 회원가입
     * <p>아이디의 중복여부 확인, 비밀번호는 암호화해서 저장
     */
    @PostMapping("/partner/register")
    public ResponseEntity<?> register(@RequestBody Auth.register request) {
        PartnerEntity entity = this.partnerService.register(request);
        return ResponseEntity.ok(entity);
    }


    /**
     * 파트너 로그인
     * 아이디와 비밀번호가 일치하는지 확인
     */
    @PostMapping("/partner/login")
    public ResponseEntity<?> login(@RequestBody Auth.login request) {
        return null;
    }
}
