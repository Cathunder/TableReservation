package com.example.tablereservation.review.dto;

import com.example.tablereservation.review.entity.ReviewEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long reservationId;
    private String storeName;
    private String contents;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReviewDto fromEntity(ReviewEntity reviewEntity) {
        return ReviewDto.builder()
                .reservationId(reviewEntity.getReservation().getId())
                .storeName(reviewEntity.getStore().getStoreName())
                .contents(reviewEntity.getContents())
                .rating(reviewEntity.getRating())
                .createdAt(reviewEntity.getCreatedAt())
                .updatedAt(reviewEntity.getUpdatedAt())
                .build();
    }
}
