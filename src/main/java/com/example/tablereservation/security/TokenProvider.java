package com.example.tablereservation.security;

import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.partner.service.PartnerService;
import com.example.tablereservation.user.service.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String KEY_ROLE = "role";
    private static final long KEY_EXPIRE_TIME = 1000 * 60 * 60; // 1시간

    private final UserService userService;
    private final PartnerService partnerService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    /**
     * 토큰생성
     */
    public String generateToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLE, role);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + KEY_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

    /**
     * 토큰에서 ROLE에 따른 인증정보 가져오기
     */
    public Authentication getAuthentication(String jwt) {
        String role = this.getUserRole(jwt);
        UserDetails userDetails;

        if ("ROLE_USER".equals(role)) {
            userDetails = this.userService.loadUserByUsername(this.getUsername(jwt));
        } else if ("ROLE_PARTNER".equals(role)) {
            userDetails = this.partnerService.loadUserByUsername(this.getUsername(jwt));
        } else {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * 토큰으로 loginId 가져오기
     */
    public String getUsername(String token) {
        return this.parseClaims(token).getSubject();
    }

    /**
     * 토큰에서 role 가져오기
     */
    public String getUserRole(String token) {
        return this.parseClaims(token).getOrDefault(KEY_ROLE, String.class).toString();
    }

    /**
     * 유효한 토큰인지 확인
     */
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        Claims claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    /**
     * 토큰을 파싱하여 claims 추출
     */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new ReservationException(ErrorCode.TOKEN_WAS_EXPIRED);
        }
    }
}
