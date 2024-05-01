package com.skkucapstone.Castardbackend.repository;

import com.skkucapstone.Castardbackend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /** 추가 메서드 정의 **/

    // 유저 아이디를 통해 유저가 쓴 모든 리뷰 조회
    Optional<List<Review>> findAllByUserId(Long userId);

    // 카페 아이디를 통해 해당 카페에 달린 모든 리뷰 조회
    Optional<List<Review>> findAllByCafeId(Long cafeId);
}