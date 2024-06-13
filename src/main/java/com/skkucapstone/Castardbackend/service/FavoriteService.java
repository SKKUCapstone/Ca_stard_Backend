package com.skkucapstone.Castardbackend.service;

import com.skkucapstone.Castardbackend.domain.Favorite;
import com.skkucapstone.Castardbackend.domain.User;
import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.repository.FavoriteRepository;
import com.skkucapstone.Castardbackend.repository.UserRepository;
import com.skkucapstone.Castardbackend.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;

    /** 즐겨찾기 추가 **/
    @Transactional
    public Favorite addFavorite(Long userId, Long cafeId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Invalid user Id:" + userId));
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() ->
                new IllegalArgumentException("Invalid cafe Id:" + cafeId));

        // 중복 즐겨찾기 방지 로직
        List<Favorite> byUserIdAndCafeId = favoriteRepository.findByUserIdAndCafeId(userId, cafeId);
        if (!byUserIdAndCafeId.isEmpty()) {
            throw new IllegalStateException("Favorite already exists for user Id:" + userId + " and cafe Id:" + cafeId);
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setCafe(cafe);
        return favoriteRepository.save(favorite);
    }

    /** 즐겨찾기 삭제 **/
    @Transactional
    public void removeFavorite(Long userId, Long cafeId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Invalid user Id:" + userId));
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() ->
                new IllegalArgumentException("Invalid cafe Id:" + cafeId));

        // 존재하지 않는 즐겨찾기 삭제 시도를 방지하는 로직
        List<Favorite> byUserIdAndCafeId = favoriteRepository.findByUserIdAndCafeId(userId, cafeId);
        if (byUserIdAndCafeId.isEmpty()) {
            throw new IllegalStateException("Favorite not exists for user Id:" + userId + " and cafe Id:" + cafeId);
        }

        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        for (Favorite favorite : favorites) {
            if (favorite.getCafe().getId().equals(cafeId)) {
                favoriteRepository.delete(favorite);
                break;
            }
        }
    }

    /** 특정 유저의 즐겨찾기 목록 조회 **/
    public List<Favorite> getUserFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    /** 특정 카페의 즐겨찾기 목록 조회 **/
    public List<Favorite> getCafeFavorites(Long cafeId) {
        return favoriteRepository.findByCafeId(cafeId);
    }
}

