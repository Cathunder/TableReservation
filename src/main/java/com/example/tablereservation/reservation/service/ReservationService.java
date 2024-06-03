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
     * 1. getReservationEntity()
     * 2. 현재 로그인된 아이디와 요청을 보낸 로그인 아이디가 동일한지 확인
     */
    public ReservationDto register(RegisterReservationDto.Request request, String loginId) {
        ReservationEntity reservationEntity = getReservationEntity(request);

        if (!loginId.equals(request.getLoginId())) {
            throw new ReservationException(ErrorCode.USER_INCORRECT);
        }

        ReservationEntity resultEntity = this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(resultEntity);
    }

    /**
     * getReservationEntity()
     * 1. 요청의 상점아이디 및 로그인아이디로 해당 상점 및 유저가 존재하는지 확인
     * 2. 예약시간을 저장하고 엔티티를 반환
     */
    private ReservationEntity getReservationEntity(RegisterReservationDto.Request request) {
        StoreEntity storeEntity = this.storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_EXIST));

        UserEntity userEntity = this.userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new ReservationException(ErrorCode.ID_NOT_EXIST));

        LocalDateTime localDateTime = LocalDateTime.of(request.getDate(), request.getTime());

        return ReservationEntity.builder()
                .user(userEntity)
                .store(storeEntity)
                .username(userEntity.getName())
                .people(request.getPeople())
                .status(ReservationStatus.REQUEST)
                .reservationDate(localDateTime)
                .build();
    }
}
