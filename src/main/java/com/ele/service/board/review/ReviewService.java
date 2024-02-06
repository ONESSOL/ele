package com.ele.service.board.review;

import com.ele.domain.board.review.Review;
import com.ele.domain.board.review.ReviewFile;
import com.ele.exception.board.BoardNotFoundException;
import com.ele.exception.board.FileUploadFailureException;
import com.ele.repository.board.review.ReviewFileRepository;
import com.ele.repository.board.review.ReviewRepository;
import com.ele.request.board.review.ReviewCreateRequest;
import com.ele.request.board.review.ReviewUpdateRequest;
import com.ele.response.board.review.ReviewCreateResponse;
import com.ele.response.board.review.ReviewDetailResponse;
import com.ele.response.board.review.ReviewListResponse;
import com.ele.response.board.review.ReviewUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Value("${upload.review_image.location}")
    private String location;
    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;

    @Transactional
    public ReviewCreateResponse saveReview(ReviewCreateRequest request) {

        if (request.getBoardFiles().get(0).isEmpty()) {
            Review review = reviewRepository.save(Review.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .fileAttached(0)
                    .build());
            return ReviewCreateResponse.toSave(review);
        } else {
            Review review = reviewRepository.save(Review.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .fileAttached(1)
                    .build());
            saveFiles(request.getBoardFiles(), review);
            return ReviewCreateResponse.toSave(review);
        }
    }

    @Transactional
    public void updateHits(Long reviewId) {
        reviewRepository.updateHits(reviewId);
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse findById(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(BoardNotFoundException::new);
        return ReviewDetailResponse.toSave(review);
    }

    @Transactional(readOnly = true)
    public Page<ReviewListResponse> findAll(Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<Review> reviewList = reviewRepository.findAll(pageRequest);
        return reviewList.map(ReviewListResponse::toSave);
    }

    @Transactional(readOnly = true)
    public Page<ReviewListResponse> findByTitleAndContents(String search, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<Review> reviewList = reviewRepository.findByTitleAndContents(search, pageRequest);
        return reviewList.map(ReviewListResponse::toSave);
    }

    @Transactional
    public ReviewUpdateResponse update(Long reviewId, ReviewUpdateRequest request) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(BoardNotFoundException::new);
        if(review.getFileAttached() == 0) {
            if(request.getBoardFiles().get(0).isEmpty()) {
                review.update(request.getTitle(), request.getContents(), 0);
            } else {
                saveFiles(request.getBoardFiles(), review);
                review.update(request.getTitle(), request.getContents(), 1);
            }
        } else {
            if(request.getBoardFiles().get(0).isEmpty()) {
                review.getReviewFiles().removeAll(review.getReviewFiles());
                review.update(request.getTitle(), request.getContents(), 0);
            } else {
                review.getReviewFiles().removeAll(review.getReviewFiles());
                saveFiles(request.getBoardFiles(), review);
                review.update(request.getTitle(), request.getContents(), 1);
            }
        }
        return ReviewUpdateResponse.toSave(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    private void saveFiles(List<MultipartFile> boardFiles, Review review) {
        for (MultipartFile boardFile : boardFiles) {
            String originalFileName = boardFile.getOriginalFilename();
            String storedFileName = UUID.randomUUID() + "_" + originalFileName;
            String savePath = location + storedFileName;
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                boardFile.transferTo(file);
            } catch (IOException e) {
                throw new FileUploadFailureException();
            }
            reviewFileRepository.save(ReviewFile.builder()
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .review(review)
                    .build());
        }
    }
}





















