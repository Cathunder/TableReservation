package com.example.tablereservation.reservation.service;

import com.example.tablereservation.common.type.ReservationStatus;
import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.reservation.dto.RegisterReservationDto;
import com.example.tablereservation.reservation.dto.ReservationDto;
import com.example.tablereservation.reservation.entity.ReservationEntity;
import com.example.tablereservation.reservation.repository.ReservationRepository;
import com.example.tablereservation.store.entity.StoreEntity;
import com.example.tablereservation.store.repository.StoreRepository;
import com.example.tablereservation.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 예약 등록
     * 1. 예약하려는 상점이 존재하는지 확인
     * 2. checkReservationAlreadyExist()
     * 3. checkReservationTime()
     * 4. createReservationEntity()
     * 5. dto로 반환
     */
    public ReservationDto register(RegisterReservationDto.Request request, UserEntity userEntity) {
        StoreEntity storeEntity = this.storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_EXIST));

        checkReservationAlreadyExist(request);
        checkReservationTime(request);

        ReservationEntity resultEntity = this.reservationRepository.save(createReservationEntity(request, storeEntity, userEntity));
        return ReservationDto.fromEntity(resultEntity);
    }

    /**
     * 매장별 예약 확인
     * 1. 매장이 존재하는지 확인
     * 2. 자기(파트너) 매장에 대한 접근인지 확인
     * 3. 매장 id에 해당하는 모든 예약 가져오기
     */
    public Page<ReservationDto> findReservations(Pageable pageable, Long storeId, PartnerEntity partnerEntity) {
        StoreEntity storeEntity = this.storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_EXIST));

        if (!storeEntity.getPartner().getId().equals(partnerEntity.getId())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        Page<ReservationEntity> reservationEntities = this.reservationRepository.findAllByStoreId(pageable, storeId);
        return reservationEntities.map(ReservationDto::fromEntity);
    }

    /**
     * 예약 승인
     * 1. checkReservationAndPartner()
     * 2. 예약 상태를 승인으로 변경
     */
    public ReservationDto approve(Long reservationId, PartnerEntity partnerEntity) {
        ReservationEntity reservationEntity = checkReservationAndPartner(reservationId, partnerEntity);

        reservationEntity.setStatus(ReservationStatus.APPROVE);
        this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(reservationEntity);
    }

    /**
     * 예약 거절
     * 1. checkReservationAndPartner()
     * 2. 예약 상태를 거절로 변경
     */
    public ReservationDto refuse(Long reservationId, PartnerEntity partnerEntity) {
        ReservationEntity reservationEntity = checkReservationAndPartner(reservationId, partnerEntity);

        reservationEntity.setStatus(ReservationStatus.REFUSE);
        this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(reservationEntity);
    }

    /**
     * 매장 도착 확인
     * 1. 예약이 존재하는지 확인
     * 2. 예약한 유저와 로그인한 유저가 동일한지 확인
     * 3. 예약이 APPROVE된 예약인지 확인
     * 4. 예약시간 10분전 시도했는지 확인
     * 5. 예약상태를 도착완료로 변경
     */
    public ReservationDto arrived(Long reservationId, UserEntity userEntity) {
        ReservationEntity reservationEntity = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_EXIST));

        if (!reservationEntity.getUser().getId().equals(userEntity.getId())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        if (!reservationEntity.getStatus().equals(ReservationStatus.APPROVE)) {
            throw new ReservationException(ErrorCode.RESERVATION_STATUS_NOT_APPROVE);
        }

        LocalDateTime reservationAt = reservationEntity.getReservationAt();
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(reservationAt.minusMinutes(10)) && now.isBefore(reservationAt)) {
            reservationEntity.setStatus(ReservationStatus.ARRIVED);
            this.reservationRepository.save(reservationEntity);
        } else {
            throw new ReservationException(ErrorCode.CAN_ARRIVED_CONFIRM_BEFORE_10MIN);
        }

        return ReservationDto.fromEntity(reservationEntity);
    }

    /**
     * 매장 사용 완료
     * 1. checkReservationAndPartner()
     * 2. 예약이 ARRIVED 상태인지 확인
     * 3. 현재시간이 예약시간 이후인지 확인
     */
    public ReservationDto complete(Long reservationId, PartnerEntity partnerEntity) {
        ReservationEntity reservationEntity = checkReservationAndPartner(reservationId, partnerEntity);

        if (reservationEntity.getStatus() != ReservationStatus.ARRIVED) {
            throw new ReservationException(ErrorCode.RESERVATION_STATUS_NOT_ARRIVED);
        }

        LocalDateTime reservationAt = reservationEntity.getReservationAt();
        LocalDateTime now = LocalDateTime.now();
        if(!now.isAfter(reservationAt)) {
            throw new ReservationException(ErrorCode.CAN_COMPLETE_AFTER_RESERVATION);
        }

        reservationEntity.setStatus(ReservationStatus.COMPLETE);
        this.reservationRepository.save(reservationEntity);
        return ReservationDto.fromEntity(reservationEntity);
    }

    /**
     * checkReservationAndPartner()
     * 1. 예약이 존재하는지 확인
     * 2. 자기(파트너) 매장의 예약인지 확인
     */
    private ReservationEntity checkReservationAndPartner(Long reservationId, PartnerEntity partnerEntity) {
        ReservationEntity reservationEntity = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_EXIST));

        StoreEntity storeEntity = reservationEntity.getStore();
        Long partnerId = storeEntity.getPartner().getId();
        if (!partnerId.equals(partnerEntity.getId())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        return reservationEntity;
    }

    /**
     * checkReservationAlreadyExist()
     * 1. 예약하려는 매장에 동일한 시간대의 예약이 존재하는지 확인
     */
    private void checkReservationAlreadyExist(RegisterReservationDto.Request request) {
        LocalDate date = request.getDate();
        LocalTime time = request.getTime();
        LocalDateTime reservationAt = LocalDateTime.of(date, time);

        boolean isExist = this.reservationRepository.existsByStoreIdAndReservationAt(request.getStoreId(), reservationAt);
        if (isExist) {
            throw new ReservationException(ErrorCode.RESERVATION_ALREADY_EXIST);
        }
    }

    /**
     * checkReservationTime() - 최소 예약가능시간 확인
     * 이용 1시간 전에는 예약 불가능
     * ex) 17:00 ~ 18:00 사이에는 18시 예약이 불가능
     */
    private void checkReservationTime(RegisterReservationDto.Request request) {
        LocalDate date = request.getDate();
        LocalTime time = request.getTime();
        LocalDateTime reservationAt = LocalDateTime.of(date, time);
        LocalDateTime nowPlusOneHour = LocalDateTime.now().plusHours(1);

        if (reservationAt.isBefore(LocalDateTime.now())) {
            throw new ReservationException(ErrorCode.CANNOT_RESERVE_BEFORE_NOW);
        }

        if (reservationAt.isBefore(nowPlusOneHour)) {
            throw new ReservationException(ErrorCode.CANNOT_RESERVE_BEFORE_1HOUR);
        }
    }

    /**
     * createReservationEntity() - reservationEntity 생성
     * 1. 요청으로 받은 정보를 엔티티에 저장
     * 1. 예약시간 엔티티에 저장
     * 2. 엔티티 반환
     */
    private ReservationEntity createReservationEntity(RegisterReservationDto.Request request,
                                                      StoreEntity storeEntity,
                                                      UserEntity userEntity
    ) {
        LocalDateTime reservationAt = LocalDateTime.of(request.getDate(), request.getTime());

        return ReservationEntity.builder()
                .user(userEntity)
                .store(storeEntity)
                .people(request.getPeople())
                .status(ReservationStatus.REQUEST)
                .reservationAt(reservationAt)
                .build();
    }
}
