package com.example.tablereservation.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String KEY_ROLE = "role";
    private static final long KEY_EXPIRE_TIME = 1000 * 60 * 60; // 1시간

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

    public String getUsername(String token) {
        return this.parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        Claims claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    /**
     * 유효한 토큰인지 확인
     */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
