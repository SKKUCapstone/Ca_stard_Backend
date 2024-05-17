package com.skkucapstone.Castardbackend.controller;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.dto.CafeDto;
import com.skkucapstone.Castardbackend.service.CafeService;
import com.skkucapstone.Castardbackend.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * DTO 없이 단순하게 개발. 테스트용임.
 * 추후 변경 예정
 *
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/cafes")
public class CafeController {

    private final CafeService cafeService;
    private final KakaoService kakaoService;

    @GetMapping
    public ResponseEntity<List<Cafe>> getAllCafes() {
        List<Cafe> cafes = cafeService.getAllCafes();
        return new ResponseEntity<>(cafes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cafe> getCafeById(@PathVariable Long id) {
        return cafeService.getCafeById(id)
                .map(cafe -> new ResponseEntity<>(cafe, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Cafe> saveCafe(@RequestBody Cafe cafe) {
        Cafe savedCafe = cafeService.saveCafe(cafe);
        return new ResponseEntity<>(savedCafe, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        cafeService.deleteCafe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/list")
    public ResponseEntity<?> searchCafes(@RequestBody CafeDto.CafeSearchRequest request) {
        // 하나라도 true 인 경우 CafeService 를 통해 검색 : DB 속 카페만을 대상으로 검색
        if (request.isPowerSocket() || request.isCapacity() || request.isQuiet() || request.isWifi() ||
                request.isTables() || request.isToilet() || request.isBright() || request.isClean()) {
            List<Cafe> cafes = cafeService.searchCafes(request);
            return ResponseEntity.ok(cafes);
        }
        // 모두 false인 경우 KakaoService를 통해 검색 : 카카오 API 에서 제공하는 리스트 그대로 제공.
        else {
            // 검색어 기반 X
            if (request.getSearchText() == null) {
                String latitude = String.valueOf(request.getY());
                String longitude = String.valueOf(request.getX());
                String radius = String.valueOf(request.getRadius());
                String page = String.valueOf(5);
                return kakaoService.getSearchCafeList(latitude, longitude, radius, page, "15");
            }
            // 검색어 기반 O
            else {
                String query = request.getSearchText();
                String latitude = String.valueOf(request.getY());
                String longitude = String.valueOf(request.getX());
                String radius = String.valueOf(request.getRadius());
                String page = String.valueOf(5);
                return kakaoService.getSearchCafeQuery(query, latitude, longitude, radius, page, "15");
            }
        }
    }


}

