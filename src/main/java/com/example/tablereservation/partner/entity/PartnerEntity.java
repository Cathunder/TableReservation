package com.example.tablereservation.partner.entity;

import com.example.tablereservation.common.entity.BaseEntity;
import com.example.tablereservation.store.entity.StoreEntity;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "partner")
@SuperBuilder
public class PartnerEntity extends BaseEntity implements UserDetails {

    @Column(unique = true)
    private String loginId;
    private String password;

    private String name;
    private String role;

    @OneToMany(mappedBy = "partner", fetch = FetchType.LAZY)
    private List<StoreEntity> stores = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return this.loginId;
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
