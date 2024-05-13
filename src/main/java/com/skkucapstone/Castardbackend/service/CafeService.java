package com.skkucapstone.Castardbackend.service;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Review;
import com.skkucapstone.Castardbackend.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeService {

    private final CafeRepository cafeRepository;

    public List<Cafe> getAllCafes() {
        return cafeRepository.findAll();
    }

    public Optional<Cafe> getCafeById(Long id) {
        return cafeRepository.findById(id);
    }

    @Transactional
    public Cafe saveCafe(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    /** 카페 리뷰가 추가될 때마다 호출해야 하는 함수. 카페 엔티티 내에 평점과 그 개수를 업데이트 해준다. **/
    @Transactional
    public Cafe updateCafeRating(Cafe cafe, Review review) {
        Cafe updateRatingCafe = cafe.addCafeRatings(review);
        return cafeRepository.save(updateRatingCafe);
    }

    @Transactional
    public void deleteCafe(Long id) {
        cafeRepository.deleteById(id);
    }
}
