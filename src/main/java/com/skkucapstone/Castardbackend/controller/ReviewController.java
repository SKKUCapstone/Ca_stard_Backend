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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.skkucapstone.Castardbackend.dto.ReviewDto.createNewCafeFromDTO;
import static com.skkucapstone.Castardbackend.dto.ReviewDto.mapToReviewShowResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final CafeService cafeService;

    /** 리뷰 작성 (해당 카페가 DB에 존재하지 않으면, DB에 저장까지 수행)**/
    @PostMapping("/post")
    public ResponseEntity<Long> saveReview(@RequestBody @Valid ReviewDto.ReviewCreateRequestDTO reviewCreateRequestDTO) {

        // 사용자 확인
        Optional<User> userById = userService.getUserById(reviewCreateRequestDTO.getUserId());
        if (userById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 카페 확인 및 필요시 생성
        Optional<Cafe> cafeById = cafeService.getCafeById(reviewCreateRequestDTO.getCafeId());
        if (cafeById.isEmpty()) {
            Cafe newCafe = createNewCafeFromDTO(reviewCreateRequestDTO);
            cafeService.saveCafe(newCafe);

            // 새로 저장한 카페 객체를 다시 조회: 영속성 컨텍스트에서 관리되도록 하기
            cafeById = cafeService.getCafeById(reviewCreateRequestDTO.getCafeId());
            if (cafeById.isEmpty()) {
                // 예상치 못한 오류로 새로 저장한 카페가 조회되지 않는 경우
                return ResponseEntity.notFound().build();
            }
        }

        // 리뷰 저장
        Review review = ReviewDto.mapToEntity(reviewCreateRequestDTO, userById.get(), cafeById.get());
        Review savedReview = reviewService.saveReview(review);

        // 성공 200 메시지 반환
        return ResponseEntity.ok(savedReview.getId());
    }

    /** 리뷰 삭제 **/
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReview(@RequestBody @Valid ReviewDto.ReviewDeleteRequestDTO reviewDeleteRequestDTO) {

        // 리뷰 작성자와 현재 로그인한 사용자와 일치 확인 로직
        Optional<Review> reviewById = reviewService.getReviewById(reviewDeleteRequestDTO.getReviewId());
        if (reviewById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            Long requestUserId = reviewDeleteRequestDTO.getUserId();
            Long reviewUserId = reviewById.get().getUser().getId();

            // 리뷰 삭제를 요청한 유저와, 리뷰 작성자가 다른 경우: 에러 리턴
            if (!Objects.equals(requestUserId, reviewUserId)) {
                return ResponseEntity.internalServerError().build();
            }
        }

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

