package com.skkucapstone.Castardbackend.controller;

import com.skkucapstone.Castardbackend.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    /**
     * 카페 추천 List Post 요청
     * @param request 요청 데이터
     * @return ResponseEntity
     */
    @PostMapping("/cafe")
    private ResponseEntity<String> searchCafe(HttpServletRequest request) {
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String page = request.getParameter("page");
        return cafeService.getSearchCafeList(latitude, longitude, page, "15");
    }

    @PutMapping("/cafe")
    private ResponseEntity<String> updateCafe() {
        return ResponseEntity.status(HttpStatus.OK).body("PutMapping!!");
    }

    @DeleteMapping("cafe")
    private ResponseEntity<String> deleteCafe() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("DeleteMapping!!");
    }

}