package com.skkucapstone.Castardbackend.controller;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.dto.CafeDto;
import com.skkucapstone.Castardbackend.service.CafeService;
import com.skkucapstone.Castardbackend.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<CafeDto.CafeDTO>> getAllCafes() {
        List<Cafe> cafes = cafeService.getAllCafes();
        List<CafeDto.CafeDTO> cafeDTOS = cafes.stream()
                .map(CafeDto::mapEntityToCafeDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(cafeDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CafeDto.CafeDTO> getCafeById(@PathVariable Long id) {
        return cafeService.getCafeById(id)
                .map(cafe -> new ResponseEntity<>(CafeDto.mapEntityToCafeDTO(cafe), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Long> saveCafe(@RequestBody Cafe cafe) {
        Cafe savedCafe = cafeService.saveCafe(cafe);
        return new ResponseEntity<>(savedCafe.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        cafeService.deleteCafe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/list")
    public ResponseEntity<?> searchCafes(@RequestParam(name = "x") double x,
                                         @RequestParam(name = "y") double y,
                                         @RequestParam(name = "radius") int radius,
                                         @RequestParam(name = "searchText", required = false) String searchText,
                                         @RequestParam(name = "filter", required = false) List<String> filter) {

        // filter 리스트의 요소를 가져옴
        boolean powerSocket = filter != null && filter.contains("powerSocket");
        boolean capacity = filter != null && filter.contains("capacity");
        boolean quiet = filter != null && filter.contains("quiet");
        boolean wifi = filter != null && filter.contains("wifi");
        boolean tables = filter != null && filter.contains("tables");
        boolean toilet = filter != null && filter.contains("toilet");
        boolean bright = filter != null && filter.contains("bright");
        boolean clean = filter != null && filter.contains("clean");

        // 필터를 주지 않거나, 빈 리스트로 주었을 경우 KakaoService를 통해 검색 : 카카오 API 에서 제공하는 리스트 그대로 제공.
        if (filter == null || filter.isEmpty()) {
            String latitude = String.valueOf(y);
            String longitude = String.valueOf(x);
            String radiusStr = String.valueOf(radius);
            String page = "5"; // 기본 페이지 번호

            if (searchText == null || searchText.isEmpty()) {
                return kakaoService.getSearchCafeList(latitude, longitude, radiusStr, page, "15");
            } else {
                return kakaoService.getSearchCafeQuery(searchText, latitude, longitude, radiusStr, page, "15");
            }
        }
        // 필터를 준 경우 CafeService 를 통해 검색 : DB 속 카페만을 대상으로 검색
        else {
            List<Cafe> cafes = cafeService.searchCafes(x, y, radius, searchText, powerSocket, capacity, quiet, wifi, tables, toilet, bright, clean);
            List<CafeDto.CafeDTO> cafeDTOS = cafes.stream()
                    .map(CafeDto::mapEntityToCafeDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(cafeDTOS);
        }
    }

}

