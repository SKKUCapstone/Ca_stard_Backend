package com.skkucapstone.Castardbackend.controller;

import com.skkucapstone.Castardbackend.domain.Favorite;
import com.skkucapstone.Castardbackend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    /** 즐겨찾기 추가 **/
    @PostMapping("/post")
    public ResponseEntity<Long> addFavorite(@RequestParam Long userId, @RequestParam Long cafeId) {
        Favorite favorite = favoriteService.addFavorite(userId, cafeId);
        return ResponseEntity.ok(favorite.getId());
    }

    /** 즐겨찾기 삭제 **/
    @DeleteMapping("/delete")
    public ResponseEntity<Void> removeFavorite(@RequestParam Long userId, @RequestParam Long cafeId) {
        favoriteService.removeFavorite(userId, cafeId);
        return ResponseEntity.noContent().build();
    }

    /** 특정 유저의 즐겨찾기 목록 조회 **/
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Favorite>> getUserFavorites(@PathVariable Long userId) {
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        return ResponseEntity.ok(favorites);
    }

    /** 특정 카페의 즐겨찾기 목록 조회 **/
    @GetMapping("/cafe/{cafeId}")
    public ResponseEntity<List<Favorite>> getCafeFavorites(@PathVariable Long cafeId) {
        List<Favorite> favorites = favoriteService.getCafeFavorites(cafeId);
        return ResponseEntity.ok(favorites);
    }
}
