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
     * 1. 요청으로 들어온 상점아이디 및 로그인아이디로 해당 상점 및 유저가 존재하는지 확인
     * 2. 현재 로그인된 아이디와 요청을 보낸 로그인 아이디가 동일한지 확인
     * 3. checkAlreadyExist()
     * 4. checkTime()
     * 5. createReservationEntity()
     * 6. 예약 생성
     */
    public ReservationDto register(RegisterReservationDto.Request request, String loginId) {
        StoreEntity storeEntity = this.storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_EXIST));
        UserEntity userEntity = this.userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new ReservationException(ErrorCode.ID_NOT_EXIST));

        if (!loginId.equals(request.getLoginId())) {
            throw new ReservationException(ErrorCode.USER_INCORRECT);
        }

        checkAlreadyExist(request);
        checkTime(request);
        ReservationEntity reservationEntity = createReservationEntity(request, storeEntity, userEntity);

        ReservationEntity resultEntity = this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(resultEntity);
    }

    /**
     * createReservationEntity() - reservationEntity 생성
     * 1. 엔티티에 예약시간 저장
     * 2. 엔티티 반환
     */
    private ReservationEntity createReservationEntity(RegisterReservationDto.Request request,
                                                      StoreEntity storeEntity,
                                                      UserEntity userEntity
    ) {
        LocalDateTime localDateTime = LocalDateTime.of(request.getDate(), request.getTime());

        return ReservationEntity.builder()
                .user(userEntity)
                .store(storeEntity)
                .people(request.getPeople())
                .status(ReservationStatus.REQUEST)
                .reservationDateTime(localDateTime)
                .build();
    }

    /**
     * checkAlreadyExist()
     * 1. 예약하려는 매장에 동일한 시간대의 예약이 존재하는지 확인
     */
    private void checkAlreadyExist(RegisterReservationDto.Request request) {
        LocalDate date = request.getDate();
        LocalTime time = request.getTime();
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time);

        boolean isExist = this.reservationRepository.existsByStoreIdAndReservationDateTime(request.getStoreId(), reservationDateTime);
        if (isExist) {
            throw new ReservationException(ErrorCode.RESERVATION_ALREADY_EXIST);
        }
    }

    /**
     * checkTime() - 최소 예약가능시간 확인
     * 1시간전에는 예약 불가능
     * ex) 17:00 ~ 18:00 사이에는 18시 예약이 불가능
     */
    private void checkTime(RegisterReservationDto.Request request) {
        LocalDate date = request.getDate();
        LocalTime time = request.getTime();
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time);
        LocalDateTime nowPlusOneHour = LocalDateTime.now().plusHours(1);

        if(reservationDateTime.isBefore(nowPlusOneHour)) {
            throw new ReservationException(ErrorCode.CANNOT_RESERVATION_BEFORE_1HOUR);
        }
    }

    /**
     * 예약 승인
     * 1. 예약이 존재하는지 확인
     * 2. 예약 상태를 승인으로 변경
     */
    public ReservationDto approve(Long reservationId) {
        ReservationEntity reservationEntity = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_EXIST));

        reservationEntity.setStatus(ReservationStatus.APPROVE);
        this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(reservationEntity);
    }

    /**
     * 예약 거절
     * 1. 예약이 존재하는지 확인
     * 2. 예약 상태를 거절로 변경
     */
    public ReservationDto refuse(Long reservationId) {
        ReservationEntity reservationEntity = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_EXIST));

        reservationEntity.setStatus(ReservationStatus.REFUSE);
        this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(reservationEntity);
    }

    /**
     * 매장 도착 확인
     * 1. 예약 및 유저가 존재하는지 확인
     * 2. 예약시간 10분전 시도했는지 확인
     * 3. 10분 사이에 시도했다면 예약상태를 변경
     */
    public ReservationDto arrived(Long reservationId, UserEntity userDto) {
        ReservationEntity reservationEntity = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_EXIST));
        this.userRepository.findById(userDto.getId())
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));

        LocalDateTime reservationDateTime = reservationEntity.getReservationDateTime();
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(reservationDateTime.minusMinutes(10)) && now.isBefore(reservationDateTime)) {
            reservationEntity.setStatus(ReservationStatus.ARRIVED);
            this.reservationRepository.save(reservationEntity);
        } else {
            throw new ReservationException(ErrorCode.CAN_ARRIVED_CONFIRM_BEFORE_10MIN);
        }

        return ReservationDto.fromEntity(reservationEntity);
    }

    /**
     * 매장 사용 완료
     * 1. 예약이 존재하는지 확인
     * 2. 예약이 ARRIVED 상태인 경우만 COMPLETE로 처리 가능
     */
    public ReservationDto complete(Long reservationId) {
        ReservationEntity reservationEntity = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_EXIST));

        if(reservationEntity.getStatus() != ReservationStatus.ARRIVED) {
            throw new ReservationException(ErrorCode.CANNOT_SET_STATUS_COMPLETE);
        }

        reservationEntity.setStatus(ReservationStatus.COMPLETE);
        this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(reservationEntity);
    }
}
