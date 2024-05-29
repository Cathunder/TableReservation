package com.example.tablereservation.store.service;

import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.partner.repository.PartnerRepository;
import com.example.tablereservation.store.dto.AddStore;
import com.example.tablereservation.store.dto.StoreDto;
import com.example.tablereservation.store.entity.StoreEntity;
import com.example.tablereservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final PartnerRepository partnerRepository;

    /**
     * 상점 등록
     * 1. 로그인한 아이디에 대한 파트너가 존재하는지 검증
     * 2. 위의 1번 아이디와 요청으로 들어온 아이디가 동일한지 검증
     * 3. 동일한 상점명인지 아닌지 검증
     */
    public StoreDto addStore(AddStore.Request request, String loginId) {
        PartnerEntity partnerEntity = partnerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ReservationException(ErrorCode.PARTNER_NOT_FOUND));

        if (!partnerEntity.getId().equals(request.getPartnerId())) {
            throw new ReservationException(ErrorCode.PARTNER_NOT_MATCHED);
        }

        if (storeRepository.existsByStoreName(request.getStoreName())) {
            throw new ReservationException(ErrorCode.STORE_ALREADY_EXIST);
        }

        StoreEntity storeEntity = storeRepository.save(AddStore.Request.toEntity(request, partnerEntity));
        return StoreDto.fromEntity(storeEntity);
    }
}
