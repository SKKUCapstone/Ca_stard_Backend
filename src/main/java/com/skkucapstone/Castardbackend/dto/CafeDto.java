package com.skkucapstone.Castardbackend.dto;

import lombok.Data;

public class CafeDto {

    /** 카페 탐색을 위한 Request 를 처리하는 DTO**/
    @Data
    public static class CafeSearchRequest {
        private double x;
        private double y;
        private int radius;
        private String searchText;
        private boolean powerSocket;
        private boolean capacity;
        private boolean quiet;
        private boolean wifi;
        private boolean tables;
        private boolean toilet;
        private boolean bright;
        private boolean clean;
    }

}
