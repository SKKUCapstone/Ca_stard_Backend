package com.skkucapstone.Castardbackend.dto;

import com.skkucapstone.Castardbackend.domain.Review;
import com.skkucapstone.Castardbackend.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class UserDto {

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

    /** 로그인을 위한 응답을 처리하는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponseDTO {
        private Long id;
        private String email;
        private String userName;
        private List<Review> reviews;
    }

    /** 로그인 시 User 엔티티를 LoginResponseDTO 로 변환하는 함수 **/
    public static LoginResponseDTO mapEntityToLoginResponseDTO(User user) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        loginResponseDTO.setId(user.getId());
        loginResponseDTO.setEmail(user.getEmail());
        loginResponseDTO.setUserName(user.getUserName());
        loginResponseDTO.setReviews(user.getReviews());

        return loginResponseDTO;
    }

    /** 회원 가입 시 LoginRequestDTO 를 회원 User 엔티티로 변환하는 함수 **/
    public static User mapLoginRequestDTOToEntity(LoginRequestDTO loginRequestDTO) {
        User user = new User();

        user.setEmail(loginRequestDTO.getEmail());
        user.setUserName(loginRequestDTO.getUsername());

        return user;
    }
}
