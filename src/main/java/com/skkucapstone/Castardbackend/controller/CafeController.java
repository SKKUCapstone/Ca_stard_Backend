package com.skkucapstone.Castardbackend.controller;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.service.CafeService;
import com.skkucapstone.Castardbackend.service.KakaoService;
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

//    /** 카페 검색 API **/
//    @GetMapping("/list")
//    public ResponseEntity<?> searchCafes(@RequestParam(name = "x") double x,
//                                         @RequestParam(name = "y") double y,
//                                         @RequestParam(name = "radius") int radius,
//                                         @RequestParam(name = "searchText", required = false) String searchText,
////                                         @RequestParam(name = "powerSocket", required = false, defaultValue = "false") boolean powerSocket,
////                                         @RequestParam(name = "capacity", required = false, defaultValue = "false") boolean capacity,
////                                         @RequestParam(name = "quiet", required = false, defaultValue = "false") boolean quiet,
////                                         @RequestParam(name = "wifi", required = false, defaultValue = "false") boolean wifi,
////                                         @RequestParam(name = "tables", required = false, defaultValue = "false") boolean tables,
////                                         @RequestParam(name = "toilet", required = false, defaultValue = "false") boolean toilet,
////                                         @RequestParam(name = "bright", required = false, defaultValue = "false") boolean bright,
////                                         @RequestParam(name = "clean", required = false, defaultValue = "false") boolean clean)
//                                         @RequestParam(name = "filter") List<String> filter) {
//
//        // filter 리스트의 요소를 가져옴
//        boolean powerSocket = filter.contains("powerSocket");
//        boolean capacity = filter.contains("powerSocket");
//        boolean quiet = filter.contains("powerSocket");
//        boolean wifi = filter.contains("powerSocket");
//        boolean tables = filter.contains("powerSocket");
//        boolean toilet = filter.contains("powerSocket");
//        boolean bright = filter.contains("powerSocket");
//        boolean clean = filter.contains("powerSocket");
//
//        // 하나라도 true 인 경우 CafeService 를 통해 검색 : DB 속 카페만을 대상으로 검색
//        if (filter.isEmpty()) {
//            List<Cafe> cafes = cafeService.searchCafes(x, y, radius, searchText, powerSocket, capacity, quiet, wifi, tables, toilet, bright, clean);
//            return ResponseEntity.ok(cafes);
//        }
////        if (powerSocket || capacity || quiet || wifi || tables || toilet || bright || clean) {
////            List<Cafe> cafes = cafeService.searchCafes(x, y, radius, searchText, powerSocket, capacity, quiet, wifi, tables, toilet, bright, clean);
////            return ResponseEntity.ok(cafes);
////        }
//
//        // 모두 false인 경우 KakaoService를 통해 검색 : 카카오 API 에서 제공하는 리스트 그대로 제공.
//        else {
//            String latitude = String.valueOf(y);
//            String longitude = String.valueOf(x);
//            String radiusStr = String.valueOf(radius);
//            String page = "5"; // 기본 페이지 번호
//
//            if (searchText == null || searchText.isEmpty()) {
//                return kakaoService.getSearchCafeList(latitude, longitude, radiusStr, page, "15");
//            } else {
//                return kakaoService.getSearchCafeQuery(searchText, latitude, longitude, radiusStr, page, "15");
//            }
//        }
//    }

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

        // 하나라도 true 인 경우 CafeService 를 통해 검색 : DB 속 카페만을 대상으로 검색
        if (filter == null || filter.isEmpty() || powerSocket || capacity || quiet || wifi || tables || toilet || bright || clean) {
            List<Cafe> cafes = cafeService.searchCafes(x, y, radius, searchText, powerSocket, capacity, quiet, wifi, tables, toilet, bright, clean);
            return ResponseEntity.ok(cafes);
        }

        // 모두 false인 경우 KakaoService를 통해 검색 : 카카오 API 에서 제공하는 리스트 그대로 제공.
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

}

