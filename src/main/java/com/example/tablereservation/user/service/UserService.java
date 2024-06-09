package com.example.tablereservation.user.service;

import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.user.dto.LoginUserDto;
import com.example.tablereservation.user.dto.RegisterUserDto;
import com.example.tablereservation.user.dto.UserDto;
import com.example.tablereservation.user.entity.UserEntity;
import com.example.tablereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.userRepository.findByLoginId(username)
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 회원가입
     * 1. 로그인 아이디가 중복인지 확인
     * 2. 비밀번호는 passwordEncoder를 통해 저장
     */
    public UserDto register(RegisterUserDto.Request request) {
        if (this.userRepository.existsByLoginId(request.getLoginId())) {
            throw new ReservationException(ErrorCode.ID_ALREADY_EXIST);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        UserEntity userEntity = this.userRepository.save(RegisterUserDto.Request.toEntity(request));
        return UserDto.toEntity(userEntity);
    }

    /**
     * 로그인
     * 1. 존재하는 유저인지 확인
     * 2. 저장된 비밀번호와 로그인시 입력한 비밀번호가 동일한지 확인
     */
    public UserEntity login(LoginUserDto user) {
        UserEntity userEntity = this.userRepository.findByLoginId(user.getLoginId())
                .orElseThrow(() -> new ReservationException(ErrorCode.ID_NOT_EXIST));

        if (!this.passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new ReservationException(ErrorCode.PASSWORD_INCORRECT);
        }

        return userEntity;
    }
}
