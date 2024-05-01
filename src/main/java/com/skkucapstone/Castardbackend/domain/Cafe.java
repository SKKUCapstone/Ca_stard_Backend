package com.skkucapstone.Castardbackend.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="cafe")
public class Cafe {

    @Id
    @Column(name="cafe_id")
    private Long id;

    private String name;

    private String address;

    private String phone;

    private Long longitude; // x좌표

    private Long latitude; // y좌표

    // 카공에 대한 리뷰들의 총합을 유지하는 필드들.
    private Long power_socket = 0L;
    private Long capacity = 0L;
    private Long quiet = 0L;
    private Long wifi = 0L;
    private Long tables = 0L;
    private Long toilet = 0L;
    private Long bright = 0L;
    private Long clean = 0L;

    // 카공에 대한 리뷰들의 개수를 유지하는 필드들. (평균 계산에 활용됨)
    private Long power_socket_cnt = 0L;
    private Long capacity_cnt = 0L;
    private Long quiet_cnt = 0L;
    private Long wifi_cnt = 0L;
    private Long tables_cnt = 0L;
    private Long toilet_cnt = 0L;
    private Long bright_cnt = 0L;
    private Long clean_cnt = 0L;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Cafe addCafeRatings(Review review) {
        if (review.getPower_socket() != 0) {
            power_socket += review.getPower_socket();
            power_socket_cnt += 1;
        }
        if (review.getCapacity() != 0) {
            capacity += review.getCapacity();
            capacity_cnt += 1;
        }
        if (review.getQuiet() != 0) {
            quiet += review.getQuiet();
            quiet_cnt += 1;
        }
        if (review.getWifi() != 0) {
            wifi += review.getWifi();
            wifi_cnt += 1;
        }
        if (review.getTables() != 0) {
            tables += review.getTables();
            tables_cnt += 1;
        }
        if (review.getToilet() != 0) {
            toilet += review.getToilet();
            toilet_cnt += 1;
        }
        if (review.getBright() != 0) {
            bright += review.getBright();
            bright_cnt += 1;
        }
        if (review.getClean() != 0) {
            clean += review.getClean();
            clean_cnt += 1;
        }

        return this;
    }

}