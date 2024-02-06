package com.ele.domain.board.review;

import com.ele.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
public class Review extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    private String title;
    private String contents;
    private int hits;
    private int fileAttached;
    @OneToMany(mappedBy = "review", cascade = ALL, orphanRemoval = true)
    private List<ReviewFile> reviewFiles = new ArrayList<>();

    @Builder
    public Review(String title, String contents, int fileAttached) {
        this.title = title;
        this.contents = contents;
        this.hits = 0;
        this.fileAttached = fileAttached;
    }

    protected Review() {
    }

    public void addReviewFile(ReviewFile reviewFile) {
        this.getReviewFiles().add(reviewFile);
    }

    public void update(String title, String contents, int fileAttached) {
        this.title = title;
        this.contents = contents;
        this.fileAttached = fileAttached;
    }
}

























