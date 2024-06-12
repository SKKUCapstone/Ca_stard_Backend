package com.skkucapstone.Castardbackend.service;

import com.skkucapstone.Castardbackend.domain.Review;
import com.skkucapstone.Castardbackend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeService cafeService;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /** 리뷰 고유 아이디를 통해 리뷰 조회 **/
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    /** 유저 아이디를 통해 유저가 쓴 모든 리뷰 조회 **/
    public Optional<List<Review>> getAllReviewsByUserId(Long userId) {
        return reviewRepository.findAllByUserId(userId);
    }

    /** 카페 아이디를 통해 해당 카페에 달린 모든 리뷰 조회 **/
    public Optional<List<Review>> getAllReviewsByCafeId(Long cafeId) {
        return reviewRepository.findAllByCafeId(cafeId);
    }

    /** 카페 리뷰를 저장 **/
    @Transactional
    public Review saveReview(Review review) {
        review.setTimestamp(LocalDateTime.now());

        // 카페 엔티티의 평점 값과 개수를 업데이트
        cafeService.updateCafeAddRating(review.getCafe(), review);

        return reviewRepository.save(review);
    }

    /** 카페 리뷰를 삭제 **/
    @Transactional
    public void deleteReview(Long id, Review review) {
        // 카페 엔티티의 평점 값과 개수를 업데이트
        cafeService.updateCafeDeleteRating(review.getCafe(), review);

        reviewRepository.deleteById(id);
    }
}
