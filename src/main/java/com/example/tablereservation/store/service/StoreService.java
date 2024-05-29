package com.example.tablereservation.store.service;

import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.partner.repository.PartnerRepository;
import com.example.tablereservation.store.dto.AddStore;
import com.example.tablereservation.store.dto.StoreDto;
import com.example.tablereservation.store.dto.UpdateStore;
import com.example.tablereservation.store.entity.StoreEntity;
import com.example.tablereservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final PartnerRepository partnerRepository;

    /**
     * 상점 등록
     * 1,2. 검증로직 진행
     * 3. 동일한 상점명이 존재하는지 검증
     * 4. 상점 등록
     */
    public StoreDto addStore(AddStore.Request request, String loginId) {
        PartnerEntity partnerEntity = authenticate(loginId, request.getPartnerId());

        if (storeRepository.existsByStoreName(request.getStoreName())) {
            throw new ReservationException(ErrorCode.STORE_ALREADY_EXIST);
        }

        StoreEntity storeEntity = storeRepository.save(AddStore.Request.toEntity(request, partnerEntity));
        return StoreDto.fromEntity(storeEntity);
    }

    /**
     * 상점 수정
     * 1,2. 검증로직 진행
     * 3. 수정하려는 상점이 존재하는지 확인
     * 4. 이미 존재하는 상점명으로 수정했는지 확인
     * 5. 상점 정보 업데이트
     */
    public StoreDto updateStore(UpdateStore.Request request, String loginId, Long storeId) {
        this.authenticate(loginId, request.getPartnerId());

        StoreEntity storeEntity = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_EXIST));

        boolean isExist = storeRepository.existsByStoreName(request.getStoreName());
        if (isExist) {
            throw new ReservationException(ErrorCode.STORE_ALREADY_EXIST);
        }

        this.update(storeEntity, request);
        StoreEntity updateStoreEntity = storeRepository.save(storeEntity);
        return StoreDto.fromEntity(updateStoreEntity);
    }

    /**
     * 상점 정보 업데이트
     */
    private void update(StoreEntity storeEntity, UpdateStore.Request request) {
        storeEntity.setStoreName(request.getStoreName());
        storeEntity.setStoreAddress(request.getStoreAddress());
        storeEntity.setStorePhone(request.getStorePhone());
        storeEntity.setStoreIntroduction(request.getStoreIntroduction());
        storeEntity.setUpdatedDate(LocalDateTime.now());
    }

    /**
     * 검증
     * 1. 파트너 존재 여부 확인
     * 2. 요청으로 들어온 파트너 id와 현재 로그인된 파트너의 id가 같은지 확인
     */
    private PartnerEntity authenticate(String loginId, Long partnerId) {
        PartnerEntity partnerEntity = partnerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ReservationException(ErrorCode.PARTNER_NOT_FOUND));

        if (!partnerEntity.getId().equals(partnerId)) {
            throw new ReservationException(ErrorCode.PARTNER_NOT_MATCHED);
        }

        return partnerEntity;
    }
}
