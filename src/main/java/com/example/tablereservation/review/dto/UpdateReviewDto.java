package com.example.tablereservation.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

public class UpdateReviewDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @Size(min = 5, message = "리뷰는 5글자이상 작성해야합니다.")
        private String contents;

        @Min(0)
        @Max(5)
        private Integer rating;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long reservationId;
        private String storeName;
        private String contents;
        private Integer rating;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedDate;

        public static Response fromDto(ReviewDto reviewDto) {
            return Response.builder()
                    .reservationId(reviewDto.getReservationId())
                    .storeName(reviewDto.getStoreName())
                    .contents(reviewDto.getContents())
                    .rating(reviewDto.getRating())
                    .createdDate(reviewDto.getCreatedDate())
                    .updatedDate(reviewDto.getUpdatedDate())
                    .build();
        }
    }
}
