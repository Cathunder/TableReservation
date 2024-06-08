package com.example.tablereservation.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

public class RegisterReviewDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull
        private Long reservationId;

        @NotBlank
        @Size(min = 5, message = "리뷰는 5글자이상 작성해야합니다.")
        private String contents;

        @NotNull(message = "평점을 선택해주세요. (0 ~ 5)")
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

        public static Response fromDto(ReviewDto reviewDto) {
            return Response.builder()
                    .reservationId(reviewDto.getReservationId())
                    .storeName(reviewDto.getStoreName())
                    .contents(reviewDto.getContents())
                    .rating(reviewDto.getRating())
                    .createdDate(reviewDto.getCreatedDate())
                    .build();
        }
    }
}
