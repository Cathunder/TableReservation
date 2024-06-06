package com.example.tablereservation.review.entity;

import com.example.tablereservation.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "review")
@SuperBuilder
public class ReviewEntity extends BaseEntity {

    private String contents;

    @Min(0)
    @Max(5)
    private Integer rating;
}
