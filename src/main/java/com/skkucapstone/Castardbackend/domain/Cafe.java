package com.skkucapstone.Castardbackend.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="cafes")
public class Cafe {

    @Id @GeneratedValue
    @Column(name="cafe_id")
    private Long id;

    private String name;

    private String address;
}