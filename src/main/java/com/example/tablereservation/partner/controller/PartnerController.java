package com.example.tablereservation.partner.controller;

import com.example.tablereservation.partner.dto.PartnerDto;
import com.example.tablereservation.partner.dto.RegisterPartnerDto;
import com.example.tablereservation.partner.dto.LoginPartnerDto;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.partner.service.PartnerService;
import com.example.tablereservation.security.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PartnerController {

    private final TokenProvider tokenProvider;
    private final PartnerService partnerService;

    /**
     * 파트너 회원가입
     */
    @PostMapping("/partner/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterPartnerDto.Request request) {
        PartnerDto partnerDto = this.partnerService.register(request);
        return ResponseEntity.ok(RegisterPartnerDto.Response.fromDto(partnerDto));
    }

    /**
     * 파트너 로그인 - 토큰값 반환
     */
    @PostMapping("/partner/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginPartnerDto request) {
        PartnerEntity partnerEntity = this.partnerService.login(request);
        String token = this.tokenProvider.generateToken(partnerEntity.getLoginId(), partnerEntity.getRole());
        return ResponseEntity.ok(token);
    }
}
