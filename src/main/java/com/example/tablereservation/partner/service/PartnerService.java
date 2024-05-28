package com.example.tablereservation.partner.service;

import com.example.tablereservation.partner.dto.Auth;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.partner.repository.PartnerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerService implements UserDetailsService {

    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.partnerRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾지 못했습니다. -> " + username));
    }

    /**
     * 회원가입
     */
    public PartnerEntity register(Auth.register partner) {
        boolean exist = this.partnerRepository.existsByLoginId(partner.getLoginId());
        if (exist) {
            throw new RuntimeException("이미 사용중인 아이디입니다.");
        }

        partner.setPassword(passwordEncoder.encode(partner.getPassword()));
        return this.partnerRepository.save(partner.toEntity());
    }

    /**
     * 로그인 시 검증
     */
    public PartnerEntity authenticate(Auth.login partner) {
        PartnerEntity partnerEntity = this.partnerRepository.findByLoginId(partner.getLoginId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        if (!this.passwordEncoder.matches(partner.getPassword(), partnerEntity.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return partnerEntity;
    }
}
