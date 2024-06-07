package com.example.tablereservation.review.dto;

import com.example.tablereservation.review.entity.ReviewEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    public static ReviewDto fromEntity(ReviewEntity reviewEntity) {
        return ReviewDto.builder()
                .reservationId(reviewEntity.getReservation().getId())
                .storeName(reviewEntity.getStore().getStoreName())
                .contents(reviewEntity.getContents())
                .rating(reviewEntity.getRating())
                .createdDate(reviewEntity.getCreatedDate())
                .updatedDate(reviewEntity.getUpdatedDate())
                .build();
    }
}
