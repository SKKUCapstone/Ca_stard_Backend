package com.skkucapstone.Castardbackend.repository;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.util.DistanceCalculator;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CafeRepositoryImpl implements CafeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cafe> findByLocationWithinRadius(double x, double y, int radius) {
        double radiusInKm = DistanceCalculator.metersToKilometers(radius);
        List<Cafe> allCafes = entityManager.createQuery("SELECT c FROM Cafe c", Cafe.class)
                .getResultList();

        return allCafes.stream()
                .filter(cafe -> DistanceCalculator.calculateDistance(cafe.getLatitude(), cafe.getLongitude(), y, x) <= radiusInKm)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cafe> findBySearchText(String searchText, List<Cafe> cafes) {
        return cafes.stream()
                .filter(cafe -> cafe.getName().contains(searchText) || cafe.getAddress().contains(searchText))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cafe> filterByRating(List<Cafe> cafes, boolean powerSocket, boolean capacity, boolean quiet, boolean wifi, boolean tables, boolean toilet, boolean bright, boolean clean) {
        return cafes.stream()
                .filter(cafe ->
                        (!powerSocket || cafe.getPower_socket() >= 3.5) &&
                                (!capacity || cafe.getCapacity() >= 3.5) &&
                                (!quiet || cafe.getQuiet() >= 3.5) &&
                                (!wifi || cafe.getWifi() >= 3.5) &&
                                (!tables || cafe.getTables() >= 3.5) &&
                                (!toilet || cafe.getToilet() >= 3.5) &&
                                (!bright || cafe.getBright() >= 3.5) &&
                                (!clean || cafe.getClean() >= 3.5)
                )
                .collect(Collectors.toList());
    }
}

