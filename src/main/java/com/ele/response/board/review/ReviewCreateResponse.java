package com.ele.response.board.review;

import com.ele.domain.board.review.Review;
import com.ele.domain.board.review.ReviewFile;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ReviewCreateResponse {

    private Long id;
    private String title;
    private String contents;
    private int hits;
    private LocalDateTime createdTime;
    private String createdBy;
    private int fileAttached;
    private List<String> originalFileNames;
    private List<String> storedFileNames;

    public static ReviewCreateResponse toSave(Review review) {

        ReviewCreateResponse response = new ReviewCreateResponse();
        response.setId(review.getId());
        response.setTitle(review.getTitle());
        response.setContents(review.getContents());
        response.setHits(review.getHits());
        response.setCreatedTime(review.getCreateTime());
        response.setCreatedBy(review.getCreatedBy());
        if(review.getFileAttached() == 1) {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            for(ReviewFile reviewFile : review.getReviewFiles()) {
                originalFileNameList.add(reviewFile.getOriginalFileName());
                storedFileNameList.add(reviewFile.getStoredFileName());
            }
            response.setOriginalFileNames(originalFileNameList);
            response.setStoredFileNames(storedFileNameList);
        }
        return response;
    }
}
















