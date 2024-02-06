package com.ele.response.board.review;

import com.ele.domain.board.review.Review;
import com.ele.domain.board.review.ReviewFile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ReviewUpdateResponse {

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

    public static ReviewUpdateResponse toSave(Review review) {

        ReviewUpdateResponse response = new ReviewUpdateResponse();
        response.setReviewId(review.getId());
        response.setTitle(review.getTitle());
        response.setContents(review.getContents());
        response.setHits(review.getHits());
        response.setCreatedTime(review.getCreateTime());
        response.setUpdatedTime(review.getUpdatedTime());
        response.setCreatedBy(review.getCreatedBy());
        response.setUpdatedBy(review.getUpdatedBy());
        if(review.getFileAttached() == 1) {
            response.setFileAttached(review.getFileAttached());
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            for(ReviewFile reviewFile : review.getReviewFiles()) {
                originalFileNameList.add(reviewFile.getOriginalFileName());
                storedFileNameList.add(reviewFile.getStoredFileName());
            }
            response.setOriginalFileNames(originalFileNameList);
            response.setStoredFileNames(storedFileNameList);
        } else {
            response.setFileAttached(review.getFileAttached());
        }
        return response;
    }
}




















