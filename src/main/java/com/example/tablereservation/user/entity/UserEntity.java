package com.example.tablereservation.user.entity;

import com.example.tablereservation.common.entity.BaseEntity;
import com.example.tablereservation.reservation.entity.ReservationEntity;
import com.example.tablereservation.review.entity.ReviewEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@SuperBuilder
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(unique = true)
    private String loginId;
    private String password;

    private String name;
    private String phone;

    private String role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReservationEntity> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
