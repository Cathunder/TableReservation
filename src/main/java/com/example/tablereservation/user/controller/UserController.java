package com.example.tablereservation.user.controller;

import com.example.tablereservation.security.TokenProvider;
import com.example.tablereservation.user.dto.LoginUser;
import com.example.tablereservation.user.dto.RegisterUser;
import com.example.tablereservation.user.dto.UserDto;
import com.example.tablereservation.user.entity.UserEntity;
import com.example.tablereservation.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    /**
     * 유저 회원가입
     */
    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUser.Request request) {
        UserDto userDto = this.userService.register(request);
        return ResponseEntity.ok(RegisterUser.Response.fromDto(userDto));
    }

    /**
     * 유저 로그인 - 토큰값 반환
     */
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUser request) {
        UserEntity userEntity = this.userService.login(request);
        String token = this.tokenProvider.generateToken(userEntity.getLoginId(), userEntity.getRole());
        return ResponseEntity.ok(token);
    }
}
