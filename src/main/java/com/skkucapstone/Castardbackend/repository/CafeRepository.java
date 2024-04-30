package com.skkucapstone.Castardbackend.repository;
import com.skkucapstone.Castardbackend.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    // 추가 메서드 필요시 정의.
}
