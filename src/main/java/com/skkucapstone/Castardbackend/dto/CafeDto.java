package com.skkucapstone.Castardbackend.dto;

import com.skkucapstone.Castardbackend.domain.Cafe;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CafeDto {

    /** Cafe Entity 와 동일한 구조를 가지는 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeDTO {
        private Long id;
        private String cafe_name;
        private String road_address_name;
        private String phone;
        private Double longitude;
        private Double latitude;
        private String place_url;

        private Double power_socket = 0.0;
        private Double capacity = 0.0;
        private Double quiet = 0.0;
        private Double wifi = 0.0;
        private Double tables = 0.0;
        private Double toilet = 0.0;
        private Double bright = 0.0;
        private Double clean = 0.0;

        private Long power_socket_cnt = 0L;
        private Long capacity_cnt = 0L;
        private Long quiet_cnt = 0L;
        private Long wifi_cnt = 0L;
        private Long tables_cnt = 0L;
        private Long toilet_cnt = 0L;
        private Long bright_cnt = 0L;
        private Long clean_cnt = 0L;

        private List<ReviewDto.ReviewDTO> reviews = List.of();
        private List<FavoriteDto.FavoriteDTO> favorites = List.of();
    }

    /** CafeDTO 에 리뷰와 즐겨찾기 정보만 제외한 DTO **/
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeInfoDTO {
        private Long id;
        private String cafe_name;
        private String road_address_name;
        private String phone;
        private Double longitude;
        private Double latitude;
        private String place_url;

        private Double power_socket = 0.0;
        private Double capacity = 0.0;
        private Double quiet = 0.0;
        private Double wifi = 0.0;
        private Double tables = 0.0;
        private Double toilet = 0.0;
        private Double bright = 0.0;
        private Double clean = 0.0;

        private Long power_socket_cnt = 0L;
        private Long capacity_cnt = 0L;
        private Long quiet_cnt = 0L;
        private Long wifi_cnt = 0L;
        private Long tables_cnt = 0L;
        private Long toilet_cnt = 0L;
        private Long bright_cnt = 0L;
        private Long clean_cnt = 0L;
    }

    /** 카카오 API 응답을 CafeDTO 로 변환하는 함수 **/
    public static CafeDTO fromKakaoResponse(Map<String, Object> kakaoResponse) {
        CafeDTO cafeDTO = new CafeDTO();

        cafeDTO.id = Long.valueOf((String) kakaoResponse.get("id"));
        cafeDTO.cafe_name = (String) kakaoResponse.get("place_name");
        cafeDTO.road_address_name = (String) kakaoResponse.get("road_address_name");
        cafeDTO.phone = (String) kakaoResponse.get("phone");
        cafeDTO.longitude = Double.parseDouble((String) kakaoResponse.get("x"));
        cafeDTO.latitude = Double.parseDouble((String) kakaoResponse.get("y"));
        cafeDTO.place_url = (String) kakaoResponse.get("place_url");

        return cafeDTO;
    }

    /** Cafe 엔티티를 CafeDTO 로 변환하는 함수 **/
    public static CafeDTO mapEntityToCafeDTO(Cafe cafe) {
        CafeDTO cafeDTO = new CafeDTO();

        cafeDTO.setId(cafe.getId());
        cafeDTO.setCafe_name(cafe.getCafeName());
        cafeDTO.setRoad_address_name(cafe.getAddress());
        cafeDTO.setPhone(cafe.getPhone());
        cafeDTO.setLongitude(cafe.getLongitude());
        cafeDTO.setLatitude(cafe.getLatitude());
        cafeDTO.setPlace_url(cafe.getPlace_url());

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

        cafeDTO.setReviews(cafe.getReviews().stream().map(ReviewDto::mapEntityToReviewDTO).collect(Collectors.toList()));
        cafeDTO.setFavorites(cafe.getFavorites().stream().map(FavoriteDto::mapEntityToFavoriteDTO).collect(Collectors.toList()));

        return cafeDTO;
    }

    /** Cafe 엔티티를 CafeInfoDTO 로 변환하는 함수 **/
    public static CafeInfoDTO mapEntityToCafeInfoDTO(Cafe cafe) {
        CafeInfoDTO cafeInfoDTO = new CafeInfoDTO();

        cafeInfoDTO.setId(cafe.getId());
        cafeInfoDTO.setCafe_name(cafe.getCafeName());
        cafeInfoDTO.setRoad_address_name(cafe.getAddress());
        cafeInfoDTO.setPhone(cafe.getPhone());
        cafeInfoDTO.setLongitude(cafe.getLongitude());
        cafeInfoDTO.setLatitude(cafe.getLatitude());
        cafeInfoDTO.setPlace_url(cafe.getPlace_url());

        cafeInfoDTO.setPower_socket(cafe.getPower_socket());
        cafeInfoDTO.setCapacity(cafe.getCapacity());
        cafeInfoDTO.setQuiet(cafe.getQuiet());
        cafeInfoDTO.setWifi(cafe.getWifi());
        cafeInfoDTO.setTables(cafe.getTables());
        cafeInfoDTO.setToilet(cafe.getToilet());
        cafeInfoDTO.setBright(cafe.getBright());
        cafeInfoDTO.setClean(cafe.getClean());

        cafeInfoDTO.setPower_socket_cnt(cafe.getPower_socket_cnt());
        cafeInfoDTO.setCapacity_cnt(cafe.getCapacity_cnt());
        cafeInfoDTO.setQuiet_cnt(cafe.getQuiet_cnt());
        cafeInfoDTO.setWifi_cnt(cafe.getWifi_cnt());
        cafeInfoDTO.setTables_cnt(cafe.getTables_cnt());
        cafeInfoDTO.setToilet_cnt(cafe.getToilet_cnt());
        cafeInfoDTO.setBright_cnt(cafe.getBright_cnt());
        cafeInfoDTO.setClean_cnt(cafe.getClean_cnt());

        return cafeInfoDTO;
    }
}
