package com.example.tablereservation.review.controller;

import com.example.tablereservation.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<?> register() {
        return ResponseEntity.ok(null);
    }
}
