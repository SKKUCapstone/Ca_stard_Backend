package com.skkucapstone.Castardbackend.controller;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Favorite;
import com.skkucapstone.Castardbackend.domain.User;
import com.skkucapstone.Castardbackend.dto.FavoriteDto;
import com.skkucapstone.Castardbackend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    /** 즐겨찾기 추가 **/
    @PostMapping("/post")
    public ResponseEntity<Long> addFavorite(@RequestBody FavoriteDto.FavoriteRequestDTO favoriteRequestDTO) {
        Favorite favorite = favoriteService.addFavorite(favoriteRequestDTO.getUserId(), favoriteRequestDTO.getCafeId());
        return ResponseEntity.ok(favorite.getId());
    }

    /** 즐겨찾기 삭제 **/
    @DeleteMapping("/delete")
    public ResponseEntity<Void> removeFavorite(@RequestBody FavoriteDto.FavoriteRequestDTO favoriteRequestDTO) {
        favoriteService.removeFavorite(favoriteRequestDTO.getUserId(), favoriteRequestDTO.getCafeId());
        return ResponseEntity.noContent().build();
    }

    /** 특정 유저의 즐겨찾기 목록 조회 **/
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, List<Long>>> getUserFavorites(@PathVariable Long userId) {
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        List<Long> cafeIds = favorites.stream()
                .map(favorite -> favorite.getCafe().getId())
                .collect(Collectors.toList());
        Map<String, List<Long>> response = new HashMap<>();
        response.put("cafeIds", cafeIds);
        return ResponseEntity.ok(response);
    }

    /** 특정 카페의 즐겨찾기 목록 조회 **/
    @GetMapping("/cafe/{cafeId}")
    public ResponseEntity<Map<String, List<Long>>> getCafeFavorites(@PathVariable Long cafeId) {
        List<Favorite> favorites = favoriteService.getCafeFavorites(cafeId);
        List<Long> userIds = favorites.stream()
                .map(favorite -> favorite.getUser().getId())
                .collect(Collectors.toList());
        Map<String, List<Long>> response = new HashMap<>();
        response.put("userIds", userIds);
        return ResponseEntity.ok(response);
    }

}
