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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 예약 등록
     * 1. getReservationEntity()
     * 2. canReservation()
     * 3. 현재 로그인된 아이디와 요청을 보낸 로그인 아이디가 동일한지 확인
     * 4. 예약 생성
     */
    public ReservationDto register(RegisterReservationDto.Request request, String loginId) {
        ReservationEntity reservationEntity = createReservationEntity(request);
        canReservation(request);

        if (!loginId.equals(request.getLoginId())) {
            throw new ReservationException(ErrorCode.USER_INCORRECT);
        }

        ReservationEntity resultEntity = this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(resultEntity);
    }

    /**
     * getReservationEntity() - reservationEntity 생성
     * 1. 요청으로 들어온 상점아이디 및 로그인아이디로 해당 상점 및 유저가 존재하는지 확인
     * 2. 엔티티에 예약시간을 저장
     * 3. 엔티티 반환
     */
    private ReservationEntity createReservationEntity(RegisterReservationDto.Request request) {
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
                .reservationDateTime(localDateTime)
                .build();
    }

    /**
     * canReservation() - 예약 가능 여부 확인
     * 1. 예약하려는 매장에 동일한 시간대의 예약이 존재하는지 확인
     */
    private void canReservation(RegisterReservationDto.Request request) {
        LocalDate date = request.getDate();
        LocalTime time = request.getTime();
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time);

        boolean isExist = this.reservationRepository.existsByStoreIdAndReservationDateTime(request.getStoreId(), reservationDateTime);
        if (isExist) {
            throw new ReservationException(ErrorCode.RESERVATION_ALREADY_EXIST);
        }
    }
}
