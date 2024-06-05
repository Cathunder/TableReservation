package com.example.tablereservation.reservation.controller;

import com.example.tablereservation.reservation.dto.RegisterReservationDto;
import com.example.tablereservation.reservation.dto.ReservationDto;
import com.example.tablereservation.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약하기
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reservation/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterReservationDto.Request request,
            Principal principal
    ) {
        String loginId = principal.getName();
        ReservationDto reservationDto = this.reservationService.register(request, loginId);
        return ResponseEntity.ok(RegisterReservationDto.Response.fromDto(reservationDto));
    }

    /**
     * 예약 승인
     */
    @PutMapping("/reservation/approve")
    public ResponseEntity<?> approve(@RequestParam(value = "reservationId") Long reservationId) {
        ReservationDto reservationDto = this.reservationService.approve(reservationId);
        return ResponseEntity.ok(reservationDto);
    }
}
