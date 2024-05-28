package com.example.tablereservation.partner.service;

import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.partner.dto.PartnerDto;
import com.example.tablereservation.partner.dto.RegisterPartner;
import com.example.tablereservation.partner.dto.LoginPartner;
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
     */
    public PartnerDto register(RegisterPartner.Request request) {
        boolean exist = this.partnerRepository.existsByLoginId(request.getLoginId());
        if (exist) {
            throw new ReservationException(ErrorCode.ID_ALREADY_EXIST);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        PartnerEntity entity = this.partnerRepository.save(RegisterPartner.Request.toEntity(request));
        return PartnerDto.fromEntity(entity);
    }

    /**
     * 로그인 시 검증
     */
    public PartnerEntity authenticate(LoginPartner partner) {
        PartnerEntity partnerEntity = this.partnerRepository.findByLoginId(partner.getLoginId())
                .orElseThrow(() -> new ReservationException(ErrorCode.ID_NOT_EXIST));

        if (!this.passwordEncoder.matches(partner.getPassword(), partnerEntity.getPassword())) {
            throw new ReservationException(ErrorCode.PASSWORD_INCORRECT);
        }

        return partnerEntity;
    }
}
