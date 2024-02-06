package com.ele.repository.board.review;

import com.ele.domain.board.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Page<Review> findByTitleAndContents(String search, Pageable pageable);
    void updateHits(Long id);
}
