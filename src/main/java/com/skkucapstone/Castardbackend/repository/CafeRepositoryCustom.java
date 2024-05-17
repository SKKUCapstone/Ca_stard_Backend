package com.skkucapstone.Castardbackend.repository;

import com.skkucapstone.Castardbackend.domain.Cafe;

import java.util.List;

public interface CafeRepositoryCustom {
    List<Cafe> findByLocationWithinRadius(double x, double y, int radius);
    List<Cafe> findBySearchText(String searchText, List<Cafe> cafes);
    List<Cafe> filterByRating(List<Cafe> cafes, boolean powerSocket, boolean capacity, boolean quiet, boolean wifi, boolean tables, boolean toilet, boolean bright, boolean clean);
}
