package com.skkucapstone.Castardbackend.repository;

import com.skkucapstone.Castardbackend.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    List<Favorite> findByCafeId(Long cafeId);
    List<Favorite> findByUserIdAndCafeId(Long userId, Long cafeId);
}
