package com.trueman.attractions.dto.attraction;

import com.trueman.attractions.models.Assistance;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAttraction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "Запрос на чтение данных")
public class ReadRequest {
    private Long id;

    private String name;

    private LocalDate createDate;

    private String briefDescription;

    private TypeAttraction typeAttraction;

    private Locality locality;

    private List<Assistance> assistanceList;
}
