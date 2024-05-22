package com.example.tablereservation.partner.repository;

import com.example.tablereservation.partner.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long> {
    Optional<PartnerEntity> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
