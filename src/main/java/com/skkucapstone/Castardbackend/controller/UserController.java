package com.skkucapstone.Castardbackend.controller;

import com.skkucapstone.Castardbackend.domain.User;
import com.skkucapstone.Castardbackend.dto.UserDto;
import com.skkucapstone.Castardbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.skkucapstone.Castardbackend.dto.UserDto.mapEntityToLoginResponseDTO;


/**
 *
 * 로그인 기능 제외한 다른 API 의 경우
 * DTO 없이 단순하게 개발함. 테스트용임.
 *
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /** userId로 특정 회원 조회, 유저 객체 자체를 리턴 **/
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @PostMapping
//    public ResponseEntity<User> saveUser(@RequestBody User user) {
//        User savedUser = userService.saveUser(user);
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /** 로그인 기능 **/
    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponseDTO> loginUser(@RequestBody @Valid UserDto.LoginRequestDTO loginRequestDTO) {

        Optional<User> userByEmail = userService.findUserByEmail(loginRequestDTO.getEmail());

        // User 가 DB에 존재하지 않는 경우 : DB에 유저 저장하는 회원가입 로직 실행
        if (userByEmail.isEmpty()) {
            // DTO 를 Entity 로 바꾸어 주는 함수를 정의하여, 사용
            User user = UserDto.mapLoginRequestDTOToEntity(loginRequestDTO);

            // DB에 User 저장
            User savedUser = userService.saveUser(user);

            // 생성된 User 을 Dto 로 변환
            UserDto.LoginResponseDTO loginResponseDTO = mapEntityToLoginResponseDTO(savedUser);

            // 성공 200 메시지 반환
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
        }
        // User 가 DB에 존재하는 경우
        else {
            // 생성된 User 을 Dto 로 변환
            UserDto.LoginResponseDTO loginResponseDTO = mapEntityToLoginResponseDTO(userByEmail.get());
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
        }
    }
}
