package com.trueman.attractions.models;

import com.trueman.attractions.models.enums.TypeAssistance;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    @Column(name = "typeAssistance")
    private TypeAssistance typeAssistance;

    @Description("Краткое описание")
    @Column(name = "briefDescription")
    private String briefDescription;

    @Description("Исполнитель")
    @Column(name = "performer")
    private String performer;

    @Description("Список достопримечательностей")
    @ManyToMany
    @Column(name = "attractionList")
    private List<Attraction> attractionList;


}
