package com.skkucapstone.Castardbackend.service;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Review;
import com.skkucapstone.Castardbackend.repository.CafeRepository;
import com.skkucapstone.Castardbackend.repository.CafeRepositoryImpl;
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
    private final CafeRepositoryImpl cafeRepositoryImpl;

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


    /** 전채 카페 리스트를 가져와, 위치 + 텍스트 + 평점별 필터링을 제공하는 함수 **/
    public List<Cafe> searchCafes(double x, double y, int radius, String searchText,
                                  boolean powerSocket, boolean capacity, boolean quiet,
                                  boolean wifi, boolean tables, boolean toilet,
                                  boolean bright, boolean clean) {
        List<Cafe> cafes = cafeRepositoryImpl.findByLocationWithinRadius(x, y, radius);

        if (searchText != null && !searchText.isEmpty()) {
            cafes = cafeRepositoryImpl.findBySearchText(searchText, cafes);
        }

        if (powerSocket || capacity || quiet || wifi || tables || toilet || bright || clean) {
            cafes = cafeRepositoryImpl.filterByRating(cafes, powerSocket, capacity, quiet, wifi, tables, toilet, bright, clean);
        }

        return cafes;
    }
}
