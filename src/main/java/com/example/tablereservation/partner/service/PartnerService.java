package com.example.tablereservation.partner.service;

import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.partner.dto.PartnerDto;
import com.example.tablereservation.partner.dto.RegisterPartnerDto;
import com.example.tablereservation.partner.dto.LoginPartnerDto;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.partner.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService implements UserDetailsService {

    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.partnerRepository.findByLoginId(username)
                .orElseThrow(() -> new ReservationException(ErrorCode.PARTNER_NOT_FOUND));
    }

    /**
     * 회원가입
     * 1. 로그인 아이디가 중복인지 확인
     * 2. 비밀번호는 passwordEncoder를 통해 저장
     */
    public PartnerDto register(RegisterPartnerDto.Request request) {
        if (this.partnerRepository.existsByLoginId(request.getLoginId())) {
            throw new ReservationException(ErrorCode.ID_ALREADY_EXIST);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        PartnerEntity partnerEntity = this.partnerRepository.save(RegisterPartnerDto.Request.toEntity(request));
        return PartnerDto.fromEntity(partnerEntity);
    }

    /**
     * 로그인
     * 1. 존재하는 파트너인지 확인
     * 2. 저장된 비밀번호와 로그인시 입력한 비밀번호가 동일한지 확인
     */
    public PartnerEntity login(LoginPartnerDto partner) {
        PartnerEntity partnerEntity = this.partnerRepository.findByLoginId(partner.getLoginId())
                .orElseThrow(() -> new ReservationException(ErrorCode.ID_NOT_EXIST));

        if (!this.passwordEncoder.matches(partner.getPassword(), partnerEntity.getPassword())) {
            throw new ReservationException(ErrorCode.PASSWORD_INCORRECT);
        }

        return partnerEntity;
    }
}
