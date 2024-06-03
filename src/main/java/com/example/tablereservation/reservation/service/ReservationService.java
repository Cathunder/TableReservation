package com.example.tablereservation.reservation.service;

import com.example.tablereservation.common.type.ReservationStatus;
import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.reservation.dto.RegisterReservationDto;
import com.example.tablereservation.reservation.dto.ReservationDto;
import com.example.tablereservation.reservation.entity.ReservationEntity;
import com.example.tablereservation.reservation.repository.ReservationRepository;
import com.example.tablereservation.store.entity.StoreEntity;
import com.example.tablereservation.store.repository.StoreRepository;
import com.example.tablereservation.user.entity.UserEntity;
import com.example.tablereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 예약 등록
     */
    public ReservationDto register(RegisterReservationDto.Request request, String loginId) {
        StoreEntity storeEntity = this.storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_EXIST));

        UserEntity userEntity = this.userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new ReservationException(ErrorCode.ID_NOT_EXIST));

        if (!loginId.equals(request.getLoginId())) {
            throw new ReservationException(ErrorCode.USER_INCORRECT);
        }

        LocalDateTime localDateTime = LocalDateTime.of(request.getDate(), request.getTime());

        ReservationEntity reservationEntity = ReservationEntity.builder()
                .user(userEntity)
                .store(storeEntity)
                .username(userEntity.getName())
                .people(request.getPeople())
                .status(ReservationStatus.REQUEST)
                .reservationDate(localDateTime)
                .build();

        ReservationEntity result = this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(result);
    }
}
