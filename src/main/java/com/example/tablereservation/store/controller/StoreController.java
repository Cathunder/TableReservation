package com.example.tablereservation.store.controller;

import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.store.dto.RegisterStoreDto;
import com.example.tablereservation.store.dto.SearchStoreDto;
import com.example.tablereservation.store.dto.StoreDto;
import com.example.tablereservation.store.dto.UpdateStoreDto;
import com.example.tablereservation.store.service.StoreService;
import com.example.tablereservation.common.type.SearchType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 매장 등록
     */
    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping("/store/register")
    public ResponseEntity<?> registerStore(
            @RequestBody @Valid RegisterStoreDto.Request request,
            @AuthenticationPrincipal PartnerEntity partnerEntity
    ) {
        StoreDto storeDto = this.storeService.registerStore(request, partnerEntity);
        return ResponseEntity.ok(RegisterStoreDto.Response.fromDto(storeDto));
    }

    /**
     * 매장 정보 수정
     */
    @PreAuthorize("hasRole('PARTNER')")
    @PutMapping("/store/update/{storeId}")
    public ResponseEntity<?> updateStore(
            @PathVariable("storeId") Long storeId,
            @RequestBody @Valid UpdateStoreDto.Request request,
            @AuthenticationPrincipal PartnerEntity partnerEntity
    ) {
        StoreDto storeDto = this.storeService.updateStore(storeId, request, partnerEntity);
        return ResponseEntity.ok(UpdateStoreDto.Response.fromDto(storeDto));
    }

    /**
     * 매장 삭제
     */
    @PreAuthorize("hasRole('PARTNER')")
    @DeleteMapping("/store/delete/{storeId}")
    public ResponseEntity<?> deleteStore(
            @PathVariable("storeId") Long storeId,
            @AuthenticationPrincipal PartnerEntity partnerEntity
    ) {
        this.storeService.deleteStore(storeId, partnerEntity);
        return ResponseEntity.ok("store_id: " + storeId + " -> 삭제완료");
    }

    /**
     * 매장 검색
     */
    @GetMapping("/store/list")
    public ResponseEntity<?> findStore(
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "searchType", defaultValue = "ALL") SearchType searchType,
            @RequestBody @Valid SearchStoreDto.Request request
    ) {
        Pageable pageable = Pageable.ofSize(pageSize);
        Page<StoreDto> storeList = this.storeService.findStore(pageable, searchType, request);
        Page<SearchStoreDto.Response> response = storeList.map(SearchStoreDto.Response::fromDto);
        return ResponseEntity.ok(response);
    }
}
