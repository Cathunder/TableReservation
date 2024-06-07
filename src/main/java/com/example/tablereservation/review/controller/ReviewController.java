package com.example.tablereservation.review.controller;

import com.example.tablereservation.review.dto.RegisterReviewDto;
import com.example.tablereservation.review.dto.ReviewDto;
import com.example.tablereservation.review.service.ReviewService;
import com.example.tablereservation.user.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
