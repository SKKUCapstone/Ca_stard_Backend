package com.skkucapstone.Castardbackend.dto;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Review;
import com.skkucapstone.Castardbackend.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.skkucapstone.Castardbackend.dto.CafeDto.mapEntityToCafeInfoDTO;
import static com.skkucapstone.Castardbackend.dto.UserDto.mapEntityToUserInfoDTO;

public class ReviewDto {

    /** Review Entity 와 동일한 구조를 가지는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewDTO {
        private Long id;
        private UserDto.UserInfoDTO user;
        private CafeDto.CafeInfoDTO cafe;
        private LocalDateTime timestamp;
        private String comment;

        private int power_socket;
        private int capacity;
        private int quiet;
        private int wifi;
        private int tables;
        private int toilet;
        private int bright;
        private int clean;
    }

    /** 리뷰 작성을 위한 요청을 처리하는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewCreateRequestDTO {
        @NotNull
        private Long userId;
        @NotNull
        private Long cafeId;

        // DB에 저장되어 있지 않은 카페는 리뷰 작성 시 DB에 넣어주기 위한 정보들.
        private String cafeName;
        private String address;
        private String phone;
        private Double longitude;
        private Double latitude;

        private Integer powerSocket;
        private Integer capacity;
        private Integer quiet;
        private Integer wifi;
        private Integer tables;
        private Integer toilet;
        private Integer bright;
        private Integer clean;
        private String comment;
    }


    /** 리뷰 작성 시 ReviewCreateRequestDTO 를 엔티티로 변환하는 함수 **/
    public static Review mapReviewCreateRequestDTOToEntity(ReviewCreateRequestDTO reviewRequestDTO, User user, Cafe cafe) {

        Review review = new Review();

        review.setUser(user);
        review.setCafe(cafe);

        // 안전한 null 체크 후 값 설정
        if (reviewRequestDTO.getPowerSocket() != null) {
            review.setPower_socket(reviewRequestDTO.getPowerSocket());
        }
        if (reviewRequestDTO.getCapacity() != null) {
            review.setCapacity(reviewRequestDTO.getCapacity());
        }
        if (reviewRequestDTO.getQuiet() != null) {
            review.setQuiet(reviewRequestDTO.getQuiet());
        }
        if (reviewRequestDTO.getWifi() != null) {
            review.setWifi(reviewRequestDTO.getWifi());
        }
        if (reviewRequestDTO.getTables() != null) {
            review.setTables(reviewRequestDTO.getTables());
        }
        if (reviewRequestDTO.getToilet() != null) {
            review.setToilet(reviewRequestDTO.getToilet());
        }
        if (reviewRequestDTO.getBright() != null) {
            review.setBright(reviewRequestDTO.getBright());
        }
        if (reviewRequestDTO.getClean() != null) {
            review.setClean(reviewRequestDTO.getClean());
        }
        if (reviewRequestDTO.getComment() != null) {
            review.setComment(reviewRequestDTO.getComment());
        }

        return review;
    }

    /** 리뷰 조회 시 review 엔티티를 ReviewShowResponseDTO 로 변환해주는 함수 **/
    public static ReviewDTO mapEntityToReviewDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                mapEntityToUserInfoDTO(review.getUser()),
                mapEntityToCafeInfoDTO(review.getCafe()),
                review.getTimestamp(),
                review.getComment(),
                review.getPower_socket(),
                review.getCapacity(),
                review.getQuiet(),
                review.getWifi(),
                review.getTables(),
                review.getToilet(),
                review.getBright(),
                review.getClean()
        );
    }

    public static Cafe mapReviewCreateRequestDtoToEntity(ReviewDto.ReviewCreateRequestDTO reviewCreateRequestDTO) {
        Long id = reviewCreateRequestDTO.getCafeId();
        String cafeName = reviewCreateRequestDTO.getCafeName();
        String address = reviewCreateRequestDTO.getAddress();
        String phone = reviewCreateRequestDTO.getPhone();
        Double longitude = reviewCreateRequestDTO.getLongitude();
        Double latitude = reviewCreateRequestDTO.getLatitude();

        Cafe newCafe = new Cafe();
        newCafe.setId(id);
        newCafe.setCafeName(cafeName);
        newCafe.setAddress(address);
        newCafe.setPhone(phone);
        newCafe.setLongitude(longitude);
        newCafe.setLatitude(latitude);

        return newCafe;
    }
}

