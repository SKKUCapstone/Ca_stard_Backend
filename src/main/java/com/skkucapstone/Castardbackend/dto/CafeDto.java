package com.skkucapstone.Castardbackend.dto;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Review;
import com.skkucapstone.Castardbackend.domain.Favorite;
import lombok.*;

import java.util.List;

public class CafeDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeDTO {
        private Long id;
        private String cafeName;
        private String address;
        private String phone;
        private Double longitude;
        private Double latitude;

        private Double power_socket;
        private Double capacity;
        private Double quiet;
        private Double wifi;
        private Double tables;
        private Double toilet;
        private Double bright;
        private Double clean;

        private Long power_socket_cnt;
        private Long capacity_cnt;
        private Long quiet_cnt;
        private Long wifi_cnt;
        private Long tables_cnt;
        private Long toilet_cnt;
        private Long bright_cnt;
        private Long clean_cnt;

        private List<Review> reviews;
        private List<Favorite> favorites;
    }

    public static CafeDTO mapEntityToCafeDTO(Cafe cafe) {
        CafeDTO cafeDTO = new CafeDTO();

        cafeDTO.setId(cafe.getId());
        cafeDTO.setCafeName(cafe.getCafeName());
        cafeDTO.setAddress(cafe.getAddress());
        cafeDTO.setPhone(cafe.getPhone());
        cafeDTO.setLongitude(cafe.getLongitude());
        cafeDTO.setLatitude(cafe.getLatitude());

        cafeDTO.setPower_socket(cafe.getPower_socket());
        cafeDTO.setCapacity(cafe.getCapacity());
        cafeDTO.setQuiet(cafe.getQuiet());
        cafeDTO.setWifi(cafe.getWifi());
        cafeDTO.setTables(cafe.getTables());
        cafeDTO.setToilet(cafe.getToilet());
        cafeDTO.setBright(cafe.getBright());
        cafeDTO.setClean(cafe.getClean());

        cafeDTO.setPower_socket_cnt(cafe.getPower_socket_cnt());
        cafeDTO.setCapacity_cnt(cafe.getCapacity_cnt());
        cafeDTO.setQuiet_cnt(cafe.getQuiet_cnt());
        cafeDTO.setWifi_cnt(cafe.getWifi_cnt());
        cafeDTO.setTables_cnt(cafe.getTables_cnt());
        cafeDTO.setToilet_cnt(cafe.getToilet_cnt());
        cafeDTO.setBright_cnt(cafe.getBright_cnt());
        cafeDTO.setClean_cnt(cafe.getClean_cnt());

        cafeDTO.setFavorites(cafe.getFavorites());
        cafeDTO.setReviews(cafe.getReviews());

        return cafeDTO;
    }
}
