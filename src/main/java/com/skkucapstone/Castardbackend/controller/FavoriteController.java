package com.skkucapstone.Castardbackend.controller;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Favorite;
import com.skkucapstone.Castardbackend.domain.User;
import com.skkucapstone.Castardbackend.dto.FavoriteDto;
import com.skkucapstone.Castardbackend.service.CafeService;
import com.skkucapstone.Castardbackend.service.FavoriteService;
import com.skkucapstone.Castardbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.skkucapstone.Castardbackend.dto.FavoriteDto.mapFavoriteRequestDTOToEntity;
import static com.skkucapstone.Castardbackend.dto.ReviewDto.mapReviewCreateRequestDtoToEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final CafeService cafeService;


    /** 즐겨찾기 추가 **/
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addFavorite(@RequestBody FavoriteDto.FavoriteRequestDTO favoriteRequestDTO) {
        Long userId = favoriteRequestDTO.getUserId();
        Long cafeId = favoriteRequestDTO.getCafeId();

        Map<String, String> response = new HashMap<>();

        // 카페 확인 및 필요시 생성
        Optional<Cafe> cafeById = cafeService.getCafeById(cafeId);
        if (cafeById.isEmpty()){
            Cafe newCafe = mapFavoriteRequestDTOToEntity(favoriteRequestDTO);
            cafeService.saveCafe(newCafe);

            // 새로 저장한 카페 객체를 다시 조회: 영속성 컨텍스트에서 관리되도록 하기
            cafeById = cafeService.getCafeById(favoriteRequestDTO.getCafeId());
            if (cafeById.isEmpty()) {
                // 예상치 못한 오류로 새로 저장한 카페가 조회되지 않는 경우
                return ResponseEntity.notFound().build();
            }
        }

        if (userService.getUserById(userId).isEmpty()){
            response.put("message", "userID Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Favorite favorite = favoriteService.addFavorite(userId, cafeId);
        response.put("favoriteId", favorite.getId().toString());
        response.put("message", "favorite added");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /** 즐겨찾기 삭제 **/
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> removeFavorite(@RequestParam Long userId, Long cafeId) {
        // userId 와 cafeId 는 notNull 이어야 함.
        if (userId == null || cafeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        favoriteService.removeFavorite(userId, cafeId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "favorite deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /** 특정 유저의 즐겨찾기 목록 조회 **/
    @GetMapping("/user")
    public ResponseEntity<List<FavoriteDto.FavoriteDTO>> getUserFavorites(@RequestParam Long userId) {
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        List<FavoriteDto.FavoriteDTO> favoriteDTOs = favorites.stream()
                .map(FavoriteDto::mapEntityToFavoriteDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteDTOs);
    }

    /** 특정 카페의 즐겨찾기 목록 조회 **/
    @GetMapping("/cafe")
    public ResponseEntity<List<FavoriteDto.FavoriteDTO>> getCafeFavorites(@RequestParam Long cafeId) {
        List<Favorite> favorites = favoriteService.getCafeFavorites(cafeId);
        List<FavoriteDto.FavoriteDTO> favoriteDTOs = favorites.stream()
                .map(FavoriteDto::mapEntityToFavoriteDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteDTOs);
    }


}
