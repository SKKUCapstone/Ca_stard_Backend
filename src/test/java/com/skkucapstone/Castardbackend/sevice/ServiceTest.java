package com.skkucapstone.Castardbackend.sevice;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.Review;
import com.skkucapstone.Castardbackend.domain.User;
import com.skkucapstone.Castardbackend.service.CafeService;
import com.skkucapstone.Castardbackend.service.ReviewService;
import com.skkucapstone.Castardbackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    CafeService cafeService;

    @Test
    @Rollback(value = false)
    void scenarioTest() {

        User testUser1 = new User();
        testUser1.setUsername("User1");
        testUser1.setEmail("TestEmail1");

        Cafe testCafe1 = new Cafe();
        testCafe1.setId(1111L);
        testCafe1.setName("TestCafe1");


        Review testReview = new Review();
        testReview.setCafe(testCafe1);
        testReview.setUser(testUser1);

        User user1 = userService.saveUser(testUser1);
        Cafe cafe1 = cafeService.saveCafe(testCafe1);
        Review review1 = reviewService.saveReview(testReview);

        Assertions.assertEquals(review1.getUser().getId(), testUser1.getId());
        Assertions.assertEquals(review1.getCafe().getId(), testCafe1.getId());
    }
}