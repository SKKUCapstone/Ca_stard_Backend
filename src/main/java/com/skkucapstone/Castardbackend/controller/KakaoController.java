//package com.skkucapstone.Castardbackend.controller;
//
//import com.skkucapstone.Castardbackend.service.KakaoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Controller
//@RequiredArgsConstructor
//public class KakaoController {
//
//    private final KakaoService kakaoService;
//
//    /**
//     * 카페 추천 List Post 요청
//     * @param request 요청 데이터
//     * @return ResponseEntity
//     */
//    @PostMapping("/cafe/list")
//    private ResponseEntity<String> searchCafe(HttpServletRequest request) {
//        String latitude = request.getParameter("latitude");
//        String longitude = request.getParameter("longitude");
//        String page = request.getParameter("page");
//        return kakaoService.getSearchCafeList(latitude, longitude, page, "15");
//    }
//
//    /**
//     * 질의어 기반 카페 이미지 요청
//     * @param request 요청 데이터
//     * @return ResponseEntity
//     */
//    @PostMapping("/cafe/image")
//    private ResponseEntity<String> searchCafeImage(HttpServletRequest request) {
//        String query = request.getParameter("query");
//        return kakaoService.getCafeImage(query);
//    }
//
//    @PutMapping("/cafe/list")
//    private ResponseEntity<String> updateCafe() {
//        return ResponseEntity.status(HttpStatus.OK).body("PutMapping!!");
//    }
//
//    @DeleteMapping("/cafe/list")
//    private ResponseEntity<String> deleteCafe() {
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("DeleteMapping!!");
//    }
//
//}