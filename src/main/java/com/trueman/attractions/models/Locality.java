package com.trueman.attractions.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "settlement", unique = true, nullable = false)
    private String settlement;

    @Description("Регион")
    @Column(name = "region", unique = true, nullable = false)
    private String region;

    @Description("Список достопримечательностей")
    @JsonManagedReference
    @OneToMany(mappedBy = "locality")
    private List<Attraction> attractionList;

    @Description("Широта")
    @Column(name = "latitude", unique = true, nullable = false)
    private double latitude;

    @Description("Долгота")
    @Column(name = "longitude", unique = true, nullable = false)
    private double longitude;

}
