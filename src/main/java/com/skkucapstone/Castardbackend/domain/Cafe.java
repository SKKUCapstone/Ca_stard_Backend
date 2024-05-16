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

    // 카공에 대한 리뷰들의 평균을 유지하는 필드들.
    private Double power_socket = 0D;
    private Double capacity = 0D;
    private Double quiet = 0D;
    private Double wifi = 0D;
    private Double tables = 0D;
    private Double toilet = 0D;
    private Double bright = 0D;
    private Double clean = 0D;

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
            power_socket = (power_socket * power_socket_cnt + review.getPower_socket()) / (power_socket_cnt + 1);
            power_socket_cnt += 1;
        }
        if (review.getCapacity() != 0) {
            capacity = (capacity * capacity_cnt + review.getCapacity()) / (capacity_cnt + 1);
            capacity_cnt += 1;
        }
        if (review.getQuiet() != 0) {
            quiet = (quiet * quiet_cnt + review.getQuiet()) / (quiet_cnt + 1);
            quiet_cnt += 1;
        }
        if (review.getWifi() != 0) {
            wifi = (wifi * wifi_cnt + review.getWifi()) / (wifi_cnt + 1);
            wifi_cnt += 1;
        }
        if (review.getTables() != 0) {
            tables = (tables * tables_cnt + review.getTables()) / (tables_cnt + 1);
            tables_cnt += 1;
        }
        if (review.getToilet() != 0) {
            toilet = (toilet * toilet_cnt + review.getToilet()) / (toilet_cnt + 1);
            toilet_cnt += 1;
        }
        if (review.getBright() != 0) {
            bright = (bright * bright_cnt + review.getBright()) / (bright_cnt + 1);
            bright_cnt += 1;
        }
        if (review.getClean() != 0) {
            clean = (clean * clean_cnt + review.getClean()) / (clean_cnt + 1);
            clean_cnt += 1;
        }

        return this;
    }


}