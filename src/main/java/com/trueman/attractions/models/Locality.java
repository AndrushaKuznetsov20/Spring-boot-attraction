package com.trueman.attractions.models;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "locality")
public class Locality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Description("Идентификатор местоположения")
    @Column(name = "id")
    private Long id;

    @Description("Населённый пункт")
    @Column(name = "settlement")
    private String settlement;

    @Description("Регион")
    @Column(name = "region")
    private String region;

    @Description("Список достопримечательностей")
    @OneToMany
    @Column(name = "attractionsList")
    private List<Attraction> attractionList;

    @Description("Широта")
    @Column(name = "latitude")
    private double latitude;

    @Description("Долгота")
    @Column(name = "longitude")
    private double longitude;


}
