package com.example.tablereservation.store.service;

import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.store.dto.RegisterStoreDto;
import com.example.tablereservation.store.dto.SearchStoreDto;
import com.example.tablereservation.store.dto.StoreDto;
import com.example.tablereservation.store.dto.UpdateStoreDto;
import com.example.tablereservation.store.entity.StoreEntity;
import com.example.tablereservation.store.repository.StoreRepository;
import com.example.tablereservation.common.type.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    /**
     * 매장 등록
     * 1. 동일한 매장명이 존재하는지 확인
     * 2. createStoreEntity()
     * 3. 매장 등록
     */
    public StoreDto registerStore(RegisterStoreDto.Request request, PartnerEntity partnerEntity) {
        if (storeRepository.existsByStoreName(request.getStoreName())) {
            throw new ReservationException(ErrorCode.STORE_NAME_ALREADY_EXIST);
        }

        StoreEntity storeEntity = storeRepository.save(this.createStoreEntity(request, partnerEntity));
        return StoreDto.fromEntity(storeEntity);
    }

    /**
     * 매장 정보 수정
     * 1. 해당 매장이 존재하는지 확인
     * 2. 본인의 매장인지 확인
     * 3. updateStoreEntity()
     */
    public StoreDto updateStore(Long storeId, UpdateStoreDto.Request request, PartnerEntity partnerEntity) {
        StoreEntity storeEntity = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_EXIST));

        if (!storeEntity.getPartner().getId().equals(partnerEntity.getId())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        StoreEntity updatedStoreEntity = storeRepository.save(this.updateStoreEntity(request, storeEntity));
        return StoreDto.fromEntity(updatedStoreEntity);
    }

    /**
     * 매장 삭제
     * 1. 매장이 존재하는지 확인
     * 2. 본인의 매장인지 확인
     */
    public void deleteStore(Long storeId, PartnerEntity partnerEntity) {
        StoreEntity storeEntity = this.storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_EXIST));

        if (!storeEntity.getPartner().getId().equals(partnerEntity.getId())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        storeRepository.delete(storeEntity);
    }

    /**
     * createStoreEntity()
     * 1. storeEntity 생성
     */
    private StoreEntity createStoreEntity(RegisterStoreDto.Request request, PartnerEntity partnerEntity) {
        return StoreEntity.builder()
                .partner(partnerEntity)
                .storeName(request.getStoreName())
                .storeAddress(request.getStoreAddress())
                .storePhone(request.getStorePhone())
                .storeIntroduction(request.getStoreIntroduction())
                .build();
    }

    /**
     * updateStoreEntity()
     * 1. 수정값이 있을 경우 매장정보를 수정한다.
     * 2. 수정된 날짜를 추가한다.
     */
    private StoreEntity updateStoreEntity(UpdateStoreDto.Request request, StoreEntity storeEntity) {
        if (request.getStoreName() != null) {
            storeEntity.setStoreName(request.getStoreName());
        }

        if (request.getStoreAddress() != null) {
            storeEntity.setStoreAddress(request.getStoreAddress());
        }

        if (request.getStorePhone() != null) {
            storeEntity.setStorePhone(request.getStorePhone());
        }

        if (request.getStoreIntroduction() != null) {
            storeEntity.setStoreIntroduction(request.getStoreIntroduction());
        }

        storeEntity.setUpdatedAt(LocalDateTime.now());

        return storeEntity;
    }

    /**
     * 매장 검색
     * 1. 검색타입 값이 기본값(ALL)일때 or ALL로 설정되어 있을 때
     * - 검색내용이 있으면 매장이름 또는 주소에 해당 검색내용이 포함된 모든 매장을 검색
     * - 검색내용이 없으면 모든 매장을 검색
     * 2. 매장이름으로 검색
     * 3. 매장주소로 검색
     */
    public Page<StoreDto> findStore(Pageable pageable, SearchType searchType, SearchStoreDto.Request request) {
        String searchContents = request.getSearchContents();

        if (searchType == SearchType.ALL) {
            if (!searchContents.isBlank()) {
                return this.storeRepository
                        .findAllByStoreNameContainingOrStoreAddressContaining(
                                pageable,
                                searchContents,
                                searchContents
                        )
                        .map(StoreDto::fromEntity);
            }
        }

        if (searchType == SearchType.NAME) {
            return this.storeRepository
                    .findAllByStoreNameContaining(pageable, searchContents).map(StoreDto::fromEntity);
        }

        if (searchType == SearchType.ADDRESS) {
            return this.storeRepository
                    .findAllByStoreAddressContaining(pageable, searchContents).map(StoreDto::fromEntity);
        }

        return this.storeRepository.findAll(pageable).map(StoreDto::fromEntity);
    }
}
