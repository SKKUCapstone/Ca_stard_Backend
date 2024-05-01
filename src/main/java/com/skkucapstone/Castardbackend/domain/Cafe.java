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
    private int power_socket;
    private int capacity;
    private int quiet;
    private int wifi;
    private int tables;
    private int toilet;
    private int bright;
    private int clean;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}