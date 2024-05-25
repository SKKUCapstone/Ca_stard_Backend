package com.skkucapstone.Castardbackend.dto;

import com.skkucapstone.Castardbackend.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

//    /** 로그인을 위한 응답을 처리하는 DTO **/
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class LoginResponseDTO {
//        private String email;
//        private String username;
//        private List<Review> reviews;
//    }

    /** 회원 가입 시 LoginRequestDTO 를 회원 User 엔티티로 변환하는 함수 **/
    public static User mapToEntity(LoginRequestDTO loginRequestDTO) {
        User user = new User();

        user.setEmail(loginRequestDTO.getEmail());
        user.setUserName(loginRequestDTO.getUsername());

        return user;
    }
}
