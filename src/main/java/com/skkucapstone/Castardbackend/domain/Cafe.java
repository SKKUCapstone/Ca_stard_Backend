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

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}