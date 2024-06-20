package com.skkucapstone.Castardbackend.dto;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Favorite;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.skkucapstone.Castardbackend.dto.CafeDto.mapEntityToCafeInfoDTO;
import static com.skkucapstone.Castardbackend.dto.UserDto.mapEntityToUserInfoDTO;

public class FavoriteDto {

    /** Favorite Entity 와 동일한 구조를 가지는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FavoriteDTO {
        private Long id;
        private UserDto.UserInfoDTO user;
        private CafeDto.CafeInfoDTO cafe;
    }

    /** 즐겨찾기 추가를 위한 요청을 처리하는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FavoriteRequestDTO {
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
    }

    /** FavoriteRequestDTO 를 이용해 새로운 카페 엔티티를 생성하는 함수 **/
    public static Cafe mapFavoriteRequestDTOToEntity(FavoriteRequestDTO favoriteRequestDTO) {
        Long id = favoriteRequestDTO.getCafeId();
        String cafeName = favoriteRequestDTO.getCafeName();
        String address = favoriteRequestDTO.getAddress();
        String phone = favoriteRequestDTO.getPhone();
        Double longitude = favoriteRequestDTO.getLongitude();
        Double latitude = favoriteRequestDTO.getLatitude();

        Cafe newCafe = new Cafe();
        newCafe.setId(id);
        newCafe.setCafeName(cafeName);
        newCafe.setAddress(address);
        newCafe.setPhone(phone);
        newCafe.setLongitude(longitude);
        newCafe.setLatitude(latitude);

        return newCafe;
    }

    /** Favorite 엔티티를 FavoriteDTO 로 변환하는 함수 **/
    public static FavoriteDTO mapEntityToFavoriteDTO(Favorite favorite) {
        FavoriteDTO favoriteDTO = new FavoriteDTO();

        favoriteDTO.setId(favorite.getId());
        favoriteDTO.setUser(mapEntityToUserInfoDTO(favorite.getUser()));
        favoriteDTO.setCafe(mapEntityToCafeInfoDTO(favorite.getCafe()));

        return favoriteDTO;
    }
}
