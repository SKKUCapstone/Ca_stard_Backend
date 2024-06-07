package com.skkucapstone.Castardbackend.dto;

import com.skkucapstone.Castardbackend.domain.Favorite;
import com.skkucapstone.Castardbackend.domain.Review;
import com.skkucapstone.Castardbackend.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


public class UserDto {

    /** User Entity 와 동일한 구조를 가지는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        private Long id;
        private String email;
        private String userName;

        private List<ReviewDto.ReviewDTO> reviews;
        private List<FavoriteDto.FavoriteDTO> favorites;
    }

    /** UserDTO 에 리뷰와 즐겨찾기 정보만 제와한 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfoDTO {
        private Long id;
        private String email;
        private String userName;
    }

    /** 로그인을 위한 요청을 처리하는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequestDTO {
        @NotNull
        private String email;
        @NotNull
        private String username;
    }


    /** User 엔티티를 UserDTO 로 변환하는 함수 **/
    public static UserDTO mapEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUserName(user.getUserName());

        userDTO.setReviews(user.getReviews().stream().map(ReviewDto::mapEntityToReviewDTO).collect(Collectors.toList()));
        userDTO.setFavorites(user.getFavorites().stream().map(FavoriteDto::mapEntityToFavoriteDTO).collect(Collectors.toList()));

        return userDTO;
    }

    /** User 엔티티를 UserInfoDTO 로 변환하는 함수 **/
    public static UserInfoDTO mapEntityToUserInfoDTO(User user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();

        userInfoDTO.setId(user.getId());
        userInfoDTO.setEmail(user.getEmail());
        userInfoDTO.setUserName(user.getUserName());

        return userInfoDTO;
    }

    /** 회원 가입 시 LoginRequestDTO 를 회원 User 엔티티로 변환하는 함수 **/
    public static User mapLoginRequestDTOToEntity(LoginRequestDTO loginRequestDTO) {
        User user = new User();

        user.setEmail(loginRequestDTO.getEmail());
        user.setUserName(loginRequestDTO.getUsername());

        return user;
    }
}
