package com.skkucapstone.Castardbackend.dto;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Favorite;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FavoriteDto {

    /** Favorite Entity 와 동일한 구조를 가지는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FavoriteDTO {
        private Long id;
        private Long userId;
        private Long cafeId;
    }

    /** 즐겨찾기 추가 및 삭제를 위한 요청을 처리하는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FavoriteRequestDTO {
        @NotNull
        private Long userId;
        @NotNull
        private Long cafeId;
    }

    /** Favorite 엔티티를 FavoriteDTO 로 변환하는 함수 **/
    public static FavoriteDTO mapEntityToFavoriteDTO(Favorite favorite) {
        FavoriteDTO favoriteDTO = new FavoriteDTO();

        favoriteDTO.setId(favorite.getId());
        favoriteDTO.setUserId(favorite.getUser().getId());
        favoriteDTO.setCafeId(favorite.getCafe().getId());

        return favoriteDTO;
    }
}
