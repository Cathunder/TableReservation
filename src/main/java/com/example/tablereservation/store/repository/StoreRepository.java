package com.example.tablereservation.store.repository;

import com.example.tablereservation.store.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    boolean existsByStoreName(String storeName);
}