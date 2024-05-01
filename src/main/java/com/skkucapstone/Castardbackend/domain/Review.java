package com.skkucapstone.Castardbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name="review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    private LocalDateTime timestamp;

    // 리뷰 항목들
    private int power_socket;
    private int capacity;
    private int quiet;
    private int wifi;
    private int tables;
    private int toilet;
    private int bright;
    private int clean;

}
