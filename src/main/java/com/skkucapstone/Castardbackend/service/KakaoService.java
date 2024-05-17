package com.skkucapstone.Castardbackend.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class KakaoService {

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
    public ResponseEntity<String> getSearchCafeList(String longitude, String latitude, String radius, String page, String size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK f1c681d34107bd5d150c0bc5bd616975");

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        String baseUrl = "https://dapi.kakao.com/v2/local/search/category.json?" +
                "category_group_code=CE7" +
                "&page=" + page +
                "&size="+ size +
                "&sort=distance" +
                "&radius=" + radius +
                "&x=" + latitude +
                "&y=" + longitude;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);
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
    public ResponseEntity<String> getSearchCafeQuery(String query, String longitude, String latitude, String radius, String page, String size) {
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
                "&size="+ size +
                "&sort=distance" +
                "&x=" + latitude +
                "&y=" + longitude;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);
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
