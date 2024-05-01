package com.skkucapstone.Castardbackend.repository;

import com.skkucapstone.Castardbackend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 추가 메서드 필요시 정의.
}