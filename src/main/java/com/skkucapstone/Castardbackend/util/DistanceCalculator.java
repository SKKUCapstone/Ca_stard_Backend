package com.skkucapstone.Castardbackend.util;

public class DistanceCalculator {

    private static final double EARTH_RADIUS = 6371.0; // 지구 반지름 (단위: km)

    /**
     * 두 지점 간의 거리를 계산하여 반환합니다.
     *
     * @param lat1 첫 번째 지점의 위도
     * @param lon1 첫 번째 지점의 경도
     * @param lat2 두 번째 지점의 위도
     * @param lon2 두 번째 지점의 경도
     * @return 두 지점 간의 거리 (단위: km)
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    /**
     * 미터를 킬로미터로 변환합니다.
     *
     * @param meters 미터
     * @return 킬로미터
     */
    public static double metersToKilometers(double meters) {
        return meters / 1000.0;
    }
}
