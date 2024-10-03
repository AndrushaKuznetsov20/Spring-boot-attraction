package com.trueman.attractions.dto.assistance;

import com.trueman.attractions.models.Attraction;
import com.trueman.attractions.models.enums.TypeAssistance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Запрос на чтение данных")
public class ReadRequest {
    private Long id;

    private TypeAssistance typeAssistance;

    private String briefDescription;

    private String performer;

    private List<Attraction> attractionList;
}

