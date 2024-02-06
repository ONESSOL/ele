package com.ele.domain.board.review;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class ReviewFile {

    @Id @GeneratedValue
    @Column(name = "review_file_id")
    private Long id;
    private String originalFileName;
    private String storedFileName;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public ReviewFile(String originalFileName, String storedFileName, Review review) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        createReview(review);
    }

    protected ReviewFile() {
    }

    public void createReview(Review review) {
        this.review = review;
        review.addReviewFile(this);
    }
}
