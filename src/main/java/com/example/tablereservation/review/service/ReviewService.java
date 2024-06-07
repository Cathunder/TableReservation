package com.example.tablereservation.review.service;

import com.example.tablereservation.common.type.ReservationStatus;
import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.reservation.entity.ReservationEntity;
import com.example.tablereservation.reservation.repository.ReservationRepository;
import com.example.tablereservation.review.dto.RegisterReviewDto;
import com.example.tablereservation.review.dto.ReviewDto;
import com.example.tablereservation.review.dto.UpdateReviewDto;
import com.example.tablereservation.review.entity.ReviewEntity;
import com.example.tablereservation.review.repository.ReviewRepository;
import com.example.tablereservation.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 작성
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
            throw new ReservationException(ErrorCode.INCORRECT_USER);
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
     * 2. 리뷰의 최초 작성자와 리뷰를 수정하려는 사람이 동일한지 확인
     * 3. 수정하려는 리뷰정보의 존재유무에 따라 리뷰정보를 수정
     */
    public ReviewDto update(Long reviewId, UpdateReviewDto.Request request, UserEntity userEntity) {
        ReviewEntity reviewEntity = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReservationException(ErrorCode.REVIEW_NOT_EXIST));

        if (!reviewEntity.getUser().getId().equals(userEntity.getId())) {
            throw new ReservationException(ErrorCode.INCORRECT_USER);
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
}
