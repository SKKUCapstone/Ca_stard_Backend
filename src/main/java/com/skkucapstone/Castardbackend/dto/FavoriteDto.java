package com.skkucapstone.Castardbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FavoriteDto {

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
}
