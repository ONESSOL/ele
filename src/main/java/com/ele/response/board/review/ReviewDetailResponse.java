package com.ele.response.board.review;

import com.ele.domain.board.review.Review;
import com.ele.domain.board.review.ReviewFile;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ReviewDetailResponse {

    private Long reviewId;
    private String title;
    private String contents;
    private int hits;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String createdBy;
    private String updatedBy;
    private int fileAttached;
    private List<String> originalFileNames;
    private List<String> storedFileNames;

    public static ReviewDetailResponse toSave(Review review) {

        ReviewDetailResponse response = new ReviewDetailResponse();
        response.setReviewId(review.getId());
        response.setTitle(review.getTitle());
        response.setContents(review.getContents());
        response.setHits(review.getHits());
        response.setCreatedTime(review.getCreateTime());
        response.setUpdatedTime(review.getUpdatedTime());
        response.setCreatedBy(review.getCreatedBy());
        response.setUpdatedBy(review.getUpdatedBy());
        if(review.getFileAttached() == 1) {
            response.setFileAttached(1);
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            for(ReviewFile reviewFile : review.getReviewFiles()) {
                originalFileNameList.add(reviewFile.getStoredFileName());
                storedFileNameList.add(reviewFile.getStoredFileName());
            }
            response.setOriginalFileNames(originalFileNameList);
            response.setStoredFileNames(storedFileNameList);
        }
        else {
            response.setFileAttached(0);
        }
        return response;
    }
}



















