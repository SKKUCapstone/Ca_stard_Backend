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
