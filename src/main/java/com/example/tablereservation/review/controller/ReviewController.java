package com.example.tablereservation.review.controller;

import com.example.tablereservation.common.entity.BaseEntity;
import com.example.tablereservation.exception.ErrorCode;
import com.example.tablereservation.exception.ReservationException;
import com.example.tablereservation.partner.entity.PartnerEntity;
import com.example.tablereservation.review.dto.RegisterReviewDto;
import com.example.tablereservation.review.dto.ReviewDto;
import com.example.tablereservation.review.dto.UpdateReviewDto;
import com.example.tablereservation.review.service.ReviewService;
import com.example.tablereservation.user.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {

    final ReviewService reviewService;

    /**
     * 리뷰작성
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("review/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterReviewDto.Request request,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        ReviewDto reviewDto = this.reviewService.register(request, userEntity);
        return ResponseEntity.ok(RegisterReviewDto.Response.fromDto(reviewDto));
    }

    /**
     * 리뷰수정
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping("review/update/{reviewId}")
    public ResponseEntity<?> update(
            @PathVariable("reviewId") Long reviewId,
            @RequestBody @Valid UpdateReviewDto.Request request,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        ReviewDto reviewDto = this.reviewService.update(reviewId, request, userEntity);
        return ResponseEntity.ok(UpdateReviewDto.Response.fromDto(reviewDto));
    }

    /**
     * 리뷰삭제
     * 1. 현재 계정이 고객인지 점주인지 확인 후 그에 맞는 로직을 실행
     */
    @PreAuthorize("hasAnyRole('USER', 'PARTNER')")
    @DeleteMapping("review/delete/{reviewId}")
    public ResponseEntity<?> delete(
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal BaseEntity userOrPartnerEntity
    ) {
        if (userOrPartnerEntity instanceof PartnerEntity) {
            PartnerEntity partnerEntity = (PartnerEntity) userOrPartnerEntity;
            this.reviewService.deleteByPartner(reviewId, partnerEntity);
        } else if (userOrPartnerEntity instanceof UserEntity) {
            UserEntity userEntity = (UserEntity) userOrPartnerEntity;
            this.reviewService.deleteByUser(reviewId, userEntity);
        } else {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        return ResponseEntity.ok("review_id: " + reviewId + " -> 삭제완료");
    }
}
