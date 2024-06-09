package com.example.tablereservation.review.service;

import com.example.tablereservation.common.type.ReservationStatus;
import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.reservation.entity.ReservationEntity;
import com.example.tablereservation.reservation.repository.ReservationRepository;
import com.example.tablereservation.review.dto.RegisterReviewDto;
import com.example.tablereservation.review.dto.ReviewDto;
import com.example.tablereservation.review.dto.UpdateReviewDto;
import com.example.tablereservation.review.entity.ReviewEntity;
import com.example.tablereservation.review.repository.ReviewRepository;
import com.example.tablereservation.store.entity.StoreEntity;
import com.example.tablereservation.store.repository.StoreRepository;
import com.example.tablereservation.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    /**
     * 리뷰등록
     * 1. 예약이 존재하는지 확인
     * 2. 예약을 한 유저와 로그인한 유저가 동일한지 확인
     * 3. 예약건이 이용완료 상태인지 확인
     * 4. 해당 예약건에 이미 리뷰를 작성했었는지 확인
     * 5. createReviewEntity()
     */
    public ReviewDto register(RegisterReviewDto.Request request, UserEntity userEntity) {
        ReservationEntity reservationEntity = this.reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_EXIST));

        if (!reservationEntity.getUser().getId().equals(userEntity.getId())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        if (!reservationEntity.getStatus().equals(ReservationStatus.COMPLETE)) {
            throw new ReservationException(ErrorCode.RESERVATION_STATUS_NOT_COMPLETE);
        }

        if (reservationEntity.getReview() != null) {
            throw new ReservationException(ErrorCode.REVIEW_ALREADY_EXIST);
        }

        ReviewEntity reviewEntity = this.reviewRepository.save(createReviewEntity(request, reservationEntity));
        return ReviewDto.fromEntity(reviewEntity);
    }

    /**
     * createReviewEntity()
     * 1. request와 reservationEntity을 받아서 reviewEntity 생성
     */
    private ReviewEntity createReviewEntity(RegisterReviewDto.Request request, ReservationEntity reservationEntity) {
        return ReviewEntity.builder()
                .reservation(reservationEntity)
                .user(reservationEntity.getUser())
                .store(reservationEntity.getStore())
                .contents(request.getContents())
                .rating(request.getRating())
                .build();
    }

    /**
     * 리뷰수정
     * 1. 리뷰가 존재하는지 확인
     * 2. 자신이 작성했었던 리뷰였는지 확인
     * 3. 수정하려는 리뷰정보의 존재유무에 따라 리뷰정보를 수정 (수정하지 않은 정보는 그대로 유지하기 위함)
     */
    public ReviewDto update(Long reviewId, UpdateReviewDto.Request request, UserEntity userEntity) {
        ReviewEntity reviewEntity = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReservationException(ErrorCode.REVIEW_NOT_EXIST));

        if (!reviewEntity.getUser().getId().equals(userEntity.getId())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        if(request.getContents() != null) {
            reviewEntity.setContents(request.getContents());
        }

        if(request.getRating() != null) {
            reviewEntity.setRating(request.getRating());
        }

        ReviewEntity resultEntity = this.reviewRepository.save(reviewEntity);
        return ReviewDto.fromEntity(resultEntity);
    }

    /**
     * 리뷰삭제 (파트너)
     * 1. 리뷰가 존재하는지 확인
     * 2. 파트너 본인의 매장리뷰인지 확인
     */
    public void deleteByPartner(Long reviewId, PartnerEntity partnerEntity) {
        ReviewEntity reviewEntity = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReservationException(ErrorCode.REVIEW_NOT_EXIST));

        StoreEntity storeEntity = reviewEntity.getStore();
        if (!storeEntity.getPartner().getId().equals(partnerEntity.getId())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        this.reviewRepository.delete(reviewEntity);
    }

    /**
     * 리뷰삭제 (유저)
     * 1. 리뷰가 존재하는지 확인
     * 2. 자신이 작성했었던 리뷰였는지 확인
     */
    public void deleteByUser(Long reviewId, UserEntity userEntity) {
        ReviewEntity reviewEntity = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReservationException(ErrorCode.REVIEW_NOT_EXIST));

        if (!reviewEntity.getUser().getId().equals(userEntity.getId())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        this.reviewRepository.delete(reviewEntity);
    }

    /**
     * 매장별 리뷰 검색
     * 1. 매장이 존재하는지 확인
     * 2. 매장 id에 해당하는 모든 리뷰 가져오기
     */
    public Page<ReviewDto> findReviews(Pageable pageable, Long storeId) {
        this.storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_EXIST));

        Page<ReviewEntity> reviewEntities = this.reviewRepository.findAllByStoreId(pageable, storeId);
        return reviewEntities.map(ReviewDto::fromEntity);
    }
}
