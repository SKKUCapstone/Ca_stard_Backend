package com.skkucapstone.Castardbackend.sevice;

import com.skkucapstone.Castardbackend.domain.Cafe;
import com.skkucapstone.Castardbackend.domain.User;
import com.skkucapstone.Castardbackend.service.CafeService;
import com.skkucapstone.Castardbackend.service.ReviewService;
import com.skkucapstone.Castardbackend.service.UserService;
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
        testUser1.setUserName("ByungChan");
        testUser1.setEmail("nicedavid98@daum.net");

        User testUser2 = new User();
        testUser2.setUserName("Jiho");
        testUser2.setEmail("jiho@naver.com");

        Cafe testCafe1 = new Cafe();
        testCafe1.setId(12345L);
        testCafe1.setCafeName("Starbucks");

        Cafe testCafe2 = new Cafe();
        testCafe2.setId(23456L);
        testCafe2.setCafeName("Mega Coffee");


//        Review testReview = new Review();
//        testReview.setCafe(testCafe1);
//        testReview.setUser(testUser1);

        User user1 = userService.saveUser(testUser1);
        Cafe cafe1 = cafeService.saveCafe(testCafe1);
        User user2 = userService.saveUser(testUser2);
        Cafe cafe2 = cafeService.saveCafe(testCafe2);

//        Review review1 = reviewService.saveReview(testReview);
//
//        Assertions.assertEquals(review1.getUser().getId(), testUser1.getId());
//        Assertions.assertEquals(review1.getCafe().getId(), testCafe1.getId());
    }
}