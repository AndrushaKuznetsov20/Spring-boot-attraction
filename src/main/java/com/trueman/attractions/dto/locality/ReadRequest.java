package com.trueman.attractions.dto.locality;

import com.trueman.attractions.models.Attraction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Запрос на чтение данных")
public class ReadRequest {
    private Long id;

    private String settlement;

    private String region;

    private List<Attraction> attractionList;

    private double latitude;

    private double longitude;
}

