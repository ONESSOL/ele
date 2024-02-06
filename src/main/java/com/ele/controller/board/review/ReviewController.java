package com.ele.controller.board.review;

import com.ele.request.board.review.ReviewCreateRequest;
import com.ele.request.board.review.ReviewUpdateRequest;
import com.ele.response.board.review.ReviewCreateResponse;
import com.ele.response.board.review.ReviewDetailResponse;
import com.ele.response.board.review.ReviewListResponse;
import com.ele.response.board.review.ReviewUpdateResponse;
import com.ele.service.board.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/save")
    public ResponseEntity<ReviewCreateResponse> saveReview(@ModelAttribute ReviewCreateRequest request) {
        return ResponseEntity.ok(reviewService.saveReview(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDetailResponse> findById(@PathVariable Long id) {
        reviewService.updateHits(id);
        return ResponseEntity.ok(reviewService.findById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ReviewListResponse>> findAll(@PageableDefault(page = 1, sort = "id")Pageable pageable) {
        return ResponseEntity.ok(reviewService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReviewListResponse>> findByTitleAndContents(@RequestParam String search,
                                                                           @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(reviewService.findByTitleAndContents(search, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewUpdateResponse> update(@PathVariable Long id, @ModelAttribute ReviewUpdateRequest request) {
        return ResponseEntity.ok(reviewService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}

















