package com.example.tablereservation.reservation.repository;

import com.example.tablereservation.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    boolean existsByStoreIdAndReservationDateTime(Long storeId, LocalDateTime reservationDateTime);
}
