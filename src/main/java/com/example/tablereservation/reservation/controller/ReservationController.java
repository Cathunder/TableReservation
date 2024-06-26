package com.example.tablereservation.reservation.controller;

import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.reservation.dto.RegisterReservationDto;
import com.example.tablereservation.reservation.dto.ReservationDto;
import com.example.tablereservation.reservation.service.ReservationService;
import com.example.tablereservation.user.entity.UserEntity;
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
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약 등록
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reservation/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterReservationDto.Request request,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        ReservationDto reservationDto = this.reservationService.register(request, userEntity);
        return ResponseEntity.ok(RegisterReservationDto.Response.fromDto(reservationDto));
    }

    /**
     * 매장별 예약 확인
     */
    @PreAuthorize("hasRole('PARTNER')")
    @GetMapping("/reservation/list")
    public ResponseEntity<?> findReservations(
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam("storeId") Long storeId,
            @AuthenticationPrincipal PartnerEntity partnerEntity
    ) {
        Pageable pageable = Pageable.ofSize(pageSize);
        Page<ReservationDto> reservationList = this.reservationService.findReservations(pageable, storeId, partnerEntity);
        return ResponseEntity.ok(reservationList);
    }

    /**
     * 예약 승인
     */
    @PreAuthorize("hasRole('PARTNER')")
    @PutMapping("/reservation/approve")
    public ResponseEntity<?> approve(
            @RequestParam(value = "reservationId") Long reservationId,
            @AuthenticationPrincipal PartnerEntity partnerEntity
    ) {
        ReservationDto reservationDto = this.reservationService.approve(reservationId, partnerEntity);
        return ResponseEntity.ok(reservationDto);
    }

    /**
     * 예약 거절
     */
    @PreAuthorize("hasRole('PARTNER')")
    @PutMapping("/reservation/refuse")
    public ResponseEntity<?> refuse(
            @RequestParam(value = "reservationId") Long reservationId,
            @AuthenticationPrincipal PartnerEntity partnerEntity
    ) {
        ReservationDto reservationDto = this.reservationService.refuse(reservationId, partnerEntity);
        return ResponseEntity.ok(reservationDto);
    }

    /**
     * 매장 도착 확인 - 키오스크에서 로그인 후 도착확인 진행
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping("reservation/arrived")
    public ResponseEntity<?> arrived(
            @RequestParam(value = "reservationId") Long reservationId,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        ReservationDto reservationDto = this.reservationService.arrived(reservationId, userEntity);
        return ResponseEntity.ok(reservationDto);
    }

    /**
     * 매장 사용 완료
     */
    @PreAuthorize("hasRole('PARTNER')")
    @PutMapping("reservation/complete")
    public ResponseEntity<?> complete(
            @RequestParam(value = "reservationId") Long reservationId,
            @AuthenticationPrincipal PartnerEntity partnerEntity
    ) {
        ReservationDto reservationDto = this.reservationService.complete(reservationId, partnerEntity);
        return ResponseEntity.ok(reservationDto);
    }
}
