package com.example.tablereservation.user.service;

import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.user.dto.LoginUser;
import com.example.tablereservation.user.dto.RegisterUser;
import com.example.tablereservation.user.dto.UserDto;
import com.example.tablereservation.user.entity.UserEntity;
import com.example.tablereservation.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
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
     */
    public UserDto register(RegisterUser.Request request) {
        boolean isExist = this.userRepository.existsByLoginId(request.getLoginId());
        if (isExist) {
            throw new ReservationException(ErrorCode.ID_ALREADY_EXIST);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserEntity userEntity = this.userRepository.save(RegisterUser.Request.toEntity(request));
        return UserDto.toEntity(userEntity);
    }

    /**
     * 로그인 시 검증
     */
    public UserEntity authenticate(LoginUser user) {
        UserEntity userEntity = this.userRepository.findByLoginId(user.getLoginId())
                .orElseThrow(() -> new ReservationException(ErrorCode.ID_NOT_EXIST));

        if (!this.passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new ReservationException(ErrorCode.PASSWORD_INCORRECT);
        }

        return userEntity;
    }
}
