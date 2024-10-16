package com.trueman.attractions.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trueman.attractions.models.enums.TypeAssistance;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Класс, представляющий услугу
 */
@Data
@Entity
@RequiredArgsConstructor
@Table(name = "assistance")
public class Assistance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Description("Идентификатор сопровождения")
    @Column(name = "id")
    private Long id;

    @Description("Тип услуги")
    @Enumerated(EnumType.STRING)
    @Column(name = "typeAssistance", nullable = false)
    private TypeAssistance typeAssistance;

    @Description("Краткое описание")
    @Column(name = "briefDescription", nullable = false)
    private String briefDescription;

    @Description("Исполнитель")
    @Column(name = "performer", nullable = false)
    private String performer;

    @Description("Список достопримечательностей")
//    @JsonManagedReference(value = "attractionReference")
    @ManyToMany
    @JoinTable
    private List<Attraction> attractionList;

}
