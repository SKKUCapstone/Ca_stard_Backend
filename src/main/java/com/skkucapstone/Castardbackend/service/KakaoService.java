package com.skkucapstone.Castardbackend.service;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.dto.CafeDto;
import com.skkucapstone.Castardbackend.dto.FavoriteDto;
import com.skkucapstone.Castardbackend.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoService {

    private final CafeService cafeService;

    /**
     * 좌표 기준 카페 데이터 조회
     * 파라미터 순서는 x, y 순서로 넘겨줘야 데이터 리턴 받을 수 있음
     * @param longitude 경도 -> x
     * @param latitude 위도 -> y
     * @param radius 반경
     * @param page 페이지 수 1,2,3
     * @param size 페이지당 조회 수 15
     * @return ResponseEntity
     */
    public ResponseEntity<List<CafeDto.CafeDTO>> getSearchCafeList(String longitude, String latitude, String radius, String page, String size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK f1c681d34107bd5d150c0bc5bd616975");

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        String baseUrl = "https://dapi.kakao.com/v2/local/search/category.json?" +
                "category_group_code=CE7" +
                "&page=" + page +
                "&size=" + size +
                "&sort=distance" +
                "&radius=" + radius +
                "&x=" + latitude +
                "&y=" + longitude;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Map.class);

        List<CafeDto.CafeDTO> cafes = ((List<Map<String, Object>>) response.getBody().get("documents"))
                .stream()
                .map(CafeDto::fromKakaoResponse)
                .collect(Collectors.toList());

        // Cafe ID로 DB를 뒤져서, DB에 이미 존재하는 카페들은 리뷰 정보를 가져오는 로직
        for (CafeDto.CafeDTO cafeDTO : cafes) {
            Optional<Cafe> cafeById = cafeService.getCafeById(cafeDTO.getId());
            if (cafeById.isPresent()) {
                Cafe cafe = cafeById.get();

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
            }
        }

        return ResponseEntity.ok(cafes);
    }

    /**
     * 좌표 기준 카페 데이터 조회
     * 파라미터 순서는 x, y 순서로 넘겨줘야 데이터 리턴 받을 수 있음
     * @param query 검색어
     * @param longitude 경도 -> x
     * @param latitude 위도 -> y
     * @param radius 반경
     * @param page 페이지 수 1,2,3
     * @param size 페이지당 조회 수 15
     * @return ResponseEntity
     */
    public ResponseEntity<List<CafeDto.CafeDTO>> getSearchCafeQuery(String query, String longitude, String latitude, String radius, String page, String size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK f1c681d34107bd5d150c0bc5bd616975");

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        String baseUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?" +
                "category_group_code=CE7" +
                "&query=" + query +
                "&radius=" + radius +
                "&page=" + page +
                "&size=" + size +
                "&sort=distance" +
                "&x=" + latitude +
                "&y=" + longitude;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Map.class);

        List<CafeDto.CafeDTO> cafes = ((List<Map<String, Object>>) response.getBody().get("documents"))
                .stream()
                .map(CafeDto::fromKakaoResponse)
                .collect(Collectors.toList());

        // Cafe ID로 DB를 뒤져서, DB에 이미 존재하는 카페들은 리뷰 정보를 가져오는 로직
        for (CafeDto.CafeDTO cafeDTO : cafes) {
            Optional<Cafe> cafeById = cafeService.getCafeById(cafeDTO.getId());
            if (cafeById.isPresent()) {
                Cafe cafe = cafeById.get();

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
            }
        }

        return ResponseEntity.ok(cafes);
    }


    /**
     * 질의어를 통해 카페 이미지 검색
     * @param query 검색을 원하는 질의어
     * @return ResponseEntity
     */
    public ResponseEntity<String> getCafeImage(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK f1c681d34107bd5d150c0bc5bd616975");

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        String baseUrl = "https://dapi.kakao.com/v2/search/image?" +
                "query=" + query;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);
    }


}
