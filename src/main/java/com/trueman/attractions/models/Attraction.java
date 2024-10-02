package com.trueman.attractions.models;

import com.trueman.attractions.models.enums.TypeAttraction;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    @Column(name = "name")
    private String name;

    @Description("Дата создания")
    @Column(name = "CreationDate")
    private LocalDate createDate;

    @Description("Краткое описание")
    @Column(name = "briefDescription")
    private String briefDescription;

    @Description("Тип достопримечательности")
    @Column(name = "typeAttraction")
    private TypeAttraction typeAttraction;

    @Description("Местоположение")
    @ManyToOne
    @Column(name = "locality")
    private Locality locality;

    @Description("Список сопровождений (услуг)")
    @OneToMany
    @Column(name = "assistanceList")
    private List<Attraction> attractionList;

    public void setCreateDate() {
        this.createDate = LocalDate.now();
    }
}
