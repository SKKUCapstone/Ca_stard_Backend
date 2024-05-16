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
            power_socket_cnt += 1;
            power_socket = (power_socket + review.getPower_socket()) / power_socket_cnt;
        }
        if (review.getCapacity() != 0) {
            capacity_cnt += 1;
            capacity = (capacity + review.getPower_socket()) / capacity_cnt;
        }
        if (review.getQuiet() != 0) {
            quiet_cnt += 1;
            quiet = (quiet + review.getPower_socket()) / quiet_cnt;
        }
        if (review.getWifi() != 0) {
            wifi_cnt += 1;
            wifi = (wifi + review.getPower_socket()) / wifi_cnt;
        }
        if (review.getTables() != 0) {
            tables_cnt += 1;
            tables = (tables + review.getPower_socket()) / tables_cnt;
        }
        if (review.getToilet() != 0) {
            toilet_cnt += 1;
            toilet = (toilet + review.getPower_socket()) / toilet_cnt;
        }
        if (review.getBright() != 0) {
            bright_cnt += 1;
            bright = (bright + review.getPower_socket()) / bright_cnt;
        }
        if (review.getClean() != 0) {
            clean_cnt += 1;
            clean = (clean + review.getPower_socket()) / clean_cnt;
        }

        return this;
    }

}