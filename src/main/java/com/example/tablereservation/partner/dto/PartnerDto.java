package com.example.tablereservation.partner.dto;

import com.example.tablereservation.partner.entity.PartnerEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PartnerDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String role;

    public static PartnerDto fromEntity(PartnerEntity partnerEntity) {
        return PartnerDto.builder()
                .id(partnerEntity.getId())
                .loginId(partnerEntity.getLoginId())
                .password(partnerEntity.getPassword())
                .name(partnerEntity.getName())
                .role(partnerEntity.getRole())
                .build();
    }
}
