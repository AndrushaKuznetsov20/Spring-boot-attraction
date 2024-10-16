package com.trueman.attractions.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trueman.attractions.models.enums.TypeAttraction;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс, представляющий достопримечательность
 */
@Data
@Entity
@RequiredArgsConstructor
@Table(name = "attraction")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Description("Идентификатор достопримечательности")
    @Column(name = "id")
    private Long id;

    @Description("Наименование достопримечательности")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Description("Дата создания")
    @Column(name = "CreationDate")
    private LocalDate createDate;

    @Description("Краткое описание")
    @Column(name = "briefDescription", nullable = false)
    private String briefDescription;

    @Description("Тип достопримечательности")
    @Enumerated(EnumType.STRING)
    @Column(name = "typeAttraction", nullable = false)
    private TypeAttraction typeAttraction;

    @Description("Местоположение")
    @JsonBackReference(value = "localityReference")
    @ManyToOne
    @JoinColumn(name = "locality", nullable = false)
    private Locality locality;

    @Description("Список сопровождений (услуг)")
    @JsonBackReference(value = "attractionReference")
    @ManyToMany(mappedBy = "attractionList")
    private List<Assistance> assistanceList;

    public void setCreateDate() {
        this.createDate = LocalDate.now();
    }
}
