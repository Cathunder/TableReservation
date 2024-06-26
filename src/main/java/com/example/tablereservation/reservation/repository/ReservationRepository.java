package com.example.tablereservation.reservation.repository;

import com.example.tablereservation.reservation.entity.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    boolean existsByStoreIdAndReservationAt(Long storeId, LocalDateTime reservationAt);
    Page<ReservationEntity> findAllByStoreId(Pageable pageable, Long storeId);
}
