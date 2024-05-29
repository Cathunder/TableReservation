package com.example.tablereservation.store.controller;

import com.example.tablereservation.store.dto.AddStore;
import com.example.tablereservation.store.dto.StoreDto;
import com.example.tablereservation.store.dto.UpdateStore;
import com.example.tablereservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 상점 등록
     */
    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping("/store/add")
    public ResponseEntity<?> addStore(@RequestBody AddStore.Request request,
                                      Principal principal)
    {
        String loginId = principal.getName();
        StoreDto storeDto = this.storeService.addStore(request, loginId);
        return ResponseEntity.ok(AddStore.Response.fromDto(storeDto));
    }

    /**
     * 상점 수정
     */
    @PreAuthorize("hasRole('PARTNER')")
    @PutMapping("/store/update/{storeId}")
    public ResponseEntity<?> updateStore(@RequestBody UpdateStore.Request request,
                                         Principal principal,
                                         @PathVariable("storeId") Long storeId) {
        String loginId = principal.getName();
        StoreDto storeDto = this.storeService.updateStore(request, loginId, storeId);
        return ResponseEntity.ok(UpdateStore.Response.fromDto(storeDto));
    }
}
