package com.skkucapstone.Castardbackend.service;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Favorite;
import com.skkucapstone.Castardbackend.domain.User;
import com.skkucapstone.Castardbackend.dto.CafeDto;
import com.skkucapstone.Castardbackend.repository.CafeRepository;
import com.skkucapstone.Castardbackend.repository.FavoriteRepository;
import com.skkucapstone.Castardbackend.repository.UserRepository;
import com.skkucapstone.Castardbackend.util.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeRecommendationService {

    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;
    private final FavoriteRepository favoriteRepository;

    private static final double RECOMMENDATION_RADIUS = 3.0; // 3km
    private static final int RECOMMENDATION_LIMIT = 7;

    public List<CafeDto.CafeDTO> recommendCafes(Long userId, double userLat, double userLon) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("잘못된 사용자 ID:" + userId));

        List<Favorite> userFavorites = favoriteRepository.findByUserId(userId);

        // 사용자가 즐겨찾기한 카페가 없으면, 반경 내의 평점이 높은 카페 추천
        if (userFavorites.isEmpty()) {
            return recommendTopRatedCafes(userLat, userLon).stream()
                    .map(CafeDto::mapEntityToCafeDTO)
                    .collect(Collectors.toList());
        }

        // 사용자의 즐겨찾기를 분석하여 선호 속성 결정
        Map<String, Double> userPreferences = analyzeUserPreferences(userFavorites);

        // 반경 내의 카페 찾기
        List<Cafe> nearbyCafes = cafeRepository.findAll().stream()
                .filter(cafe -> DistanceCalculator.calculateDistance(userLat, userLon, cafe.getLatitude(), cafe.getLongitude()) <= RECOMMENDATION_RADIUS)
                .collect(Collectors.toList());

        // 사용자 선호도에 따라 카페 순위 매기기
        List<Cafe> recommendedCafes = nearbyCafes.stream()
                .sorted(Comparator.comparingDouble(cafe -> calculateCafeScore(cafe, userPreferences)))
                .limit(RECOMMENDATION_LIMIT)
                .collect(Collectors.toList());

        // 충분한 카페가 선호도를 만족하지 않으면, 평점이 높은 카페 추가
        if (recommendedCafes.size() < RECOMMENDATION_LIMIT) {
            List<Cafe> additionalCafes = recommendTopRatedCafes(userLat, userLon);
            recommendedCafes.addAll(additionalCafes);
            recommendedCafes = recommendedCafes.stream()
                    .distinct()
                    .limit(RECOMMENDATION_LIMIT)
                    .collect(Collectors.toList());
        }

        return recommendedCafes.stream().map(CafeDto::mapEntityToCafeDTO).collect(Collectors.toList());
    }

    private Map<String, Double> analyzeUserPreferences(List<Favorite> favorites) {
        Map<String, Double> preferences = new HashMap<>();
        String[] attributes = {"power_socket", "capacity", "quiet", "wifi", "tables", "toilet", "bright", "clean"};

        for (String attribute : attributes) {
            double avg = favorites.stream()
                    .map(Favorite::getCafe)
                    .mapToDouble(cafe -> getAttributeValue(cafe, attribute))
                    .average()
                    .orElse(0.0);
            preferences.put(attribute, avg);
        }

        return preferences;
    }

    private double getAttributeValue(Cafe cafe, String attribute) {
        switch (attribute) {
            case "power_socket": return cafe.getPower_socket();
            case "capacity": return cafe.getCapacity();
            case "quiet": return cafe.getQuiet();
            case "wifi": return cafe.getWifi();
            case "tables": return cafe.getTables();
            case "toilet": return cafe.getToilet();
            case "bright": return cafe.getBright();
            case "clean": return cafe.getClean();
            default: return 0.0;
        }
    }

    private double calculateCafeScore(Cafe cafe, Map<String, Double> userPreferences) {
        double score = 0.0;
        for (Map.Entry<String, Double> entry : userPreferences.entrySet()) {
            score += getAttributeValue(cafe, entry.getKey()) * entry.getValue();
        }
        return score;
    }

    private List<Cafe> recommendTopRatedCafes(double userLat, double userLon) {
        return cafeRepository.findAll().stream()
                .filter(cafe -> DistanceCalculator.calculateDistance(userLat, userLon, cafe.getLatitude(), cafe.getLongitude()) <= RECOMMENDATION_RADIUS)
                .sorted(Comparator.comparingDouble(this::calculateOverallRating).reversed())
                .limit(RECOMMENDATION_LIMIT)
                .collect(Collectors.toList());
    }

    private double calculateOverallRating(Cafe cafe) {
        return (cafe.getPower_socket() + cafe.getCapacity() + cafe.getQuiet() + cafe.getWifi() +
                cafe.getTables() + cafe.getToilet() + cafe.getBright() + cafe.getClean()) / 8;
    }
}
