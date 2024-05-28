package com.example.tablereservation.user.dto;

import com.example.tablereservation.user.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String phone;
    private String role;

    public static UserDto toEntity(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .loginId(userEntity.getLoginId())
                .password(userEntity.getPassword())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .role(userEntity.getRole())
                .build();
    }
}
