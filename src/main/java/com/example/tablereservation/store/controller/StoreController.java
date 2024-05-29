package com.example.tablereservation.store.controller;

import com.example.tablereservation.store.dto.AddStore;
import com.example.tablereservation.store.dto.StoreDto;
import com.example.tablereservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 상점 등록
     */
    @PostMapping("/store/add")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> addStore(@RequestBody AddStore.Request request,
                                      Principal principal)
    {
        String loginId = principal.getName();
        StoreDto storeDto = this.storeService.addStore(request, loginId);
        return ResponseEntity.ok(AddStore.Response.fromDto(storeDto));
    }
}
