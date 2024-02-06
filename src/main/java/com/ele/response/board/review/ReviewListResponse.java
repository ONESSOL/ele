package com.ele.response.board.review;

import com.ele.domain.board.review.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewListResponse {

    private Long reviewId;
    private String title;
    private int hits;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String createdBy;
    private String updatedBy;

    public static ReviewListResponse toSave(Review review) {

        ReviewListResponse response = new ReviewListResponse();
        response.setReviewId(review.getId());
        response.setTitle(review.getTitle());
        response.setHits(review.getHits());
        response.setCreatedTime(review.getCreateTime());
        response.setUpdatedTime(review.getUpdatedTime());
        response.setCreatedBy(review.getCreatedBy());
        response.setUpdatedBy(review.getUpdatedBy());
        return response;
    }
}
