package com.skkucapstone.Castardbackend.controller;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Review;
import com.skkucapstone.Castardbackend.domain.User;
import com.skkucapstone.Castardbackend.dto.ReviewDto;
import com.skkucapstone.Castardbackend.service.CafeService;
import com.skkucapstone.Castardbackend.service.ReviewService;
import com.skkucapstone.Castardbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.skkucapstone.Castardbackend.dto.ReviewDto.mapToReviewShowResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final CafeService cafeService;

    /** 리뷰 작성 **/
    @PostMapping("/post")
    public ResponseEntity<Long> saveReview(@RequestBody @Valid ReviewDto.ReviewCreateRequestDTO reviewCreateRequestDTO) {

        Optional<User> userById = userService.getUserById(reviewCreateRequestDTO.getUserId());
        Optional<Cafe> cafeById = cafeService.getCafeById(reviewCreateRequestDTO.getCafeId());

        // 사용자나 카페가 존재하지 않는 경우 404 에러 반환
        if (!userById.isPresent() || !cafeById.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // DTO 를 Entity 로 바꾸어 주는 함수를 정의하여, 사용
        Review review = ReviewDto.mapToEntity(reviewCreateRequestDTO, userById.get(), cafeById.get());

        // 리뷰 저장
        Review savedReview = reviewService.saveReview(review);

        // 성공 200 메시지 반환
        return new ResponseEntity<>(savedReview.getId(), HttpStatus.OK);
    }

    /** 리뷰 삭제 **/
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReview(@RequestBody @Valid ReviewDto.ReviewDeleteRequestDTO reviewDeleteRequestDTO) {

        // 리뷰 작성자와 현재 로그인한 사용자와 일치 확인 로직은 추후 구현 예정 (카카오 로그인 구현 완료 후 가능)

        reviewService.deleteReview(reviewDeleteRequestDTO.getReviewId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** 유저 아이디를 통해 유저가 쓴 모든 리뷰 조회 **/
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDto.ReviewShowResponseDTO>> getAllReviewsByUserId(@PathVariable("userId") Long userId) {
        Optional<List<Review>> optionalReviews = reviewService.getAllReviewsByUserId(userId);

        if (optionalReviews.isPresent()) {
            List<Review> reviews = optionalReviews.get();
            List<ReviewDto.ReviewShowResponseDTO> responseDTOs = reviews.stream()
                    .map(review -> mapToReviewShowResponseDTO(review))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** 카페 아이디를 통해 해당 카페에 달린 모든 리뷰 조회 **/
    @GetMapping("/cafe/{cafeId}")
    public ResponseEntity<List<ReviewDto.ReviewShowResponseDTO>> getAllReviewsByCafeId(@PathVariable("cafeId") Long cafeId) {
        Optional<List<Review>> optionalReviews = reviewService.getAllReviewsByCafeId(cafeId);

        if (optionalReviews.isPresent()) {
            List<Review> reviews = optionalReviews.get();
            List<ReviewDto.ReviewShowResponseDTO> responseDTOs = reviews.stream()
                    .map(review -> mapToReviewShowResponseDTO(review))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

