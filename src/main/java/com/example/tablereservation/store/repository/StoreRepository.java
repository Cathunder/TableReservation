package com.example.tablereservation.store.repository;

import com.example.tablereservation.store.entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    boolean existsByStoreName(String storeName);

    Page<StoreEntity> findAllByStoreNameContaining(Pageable pageable, String storeName);
    Page<StoreEntity> findAllByStoreAddressContaining(Pageable pageable, String storeAddress);
    Page<StoreEntity> findAllByStoreNameContainingOrStoreAddressContaining(Pageable pageable, String storeName, String storeAddress);
}
