package com.trueman.attractions.dto.attraction;

import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAttraction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание достопримечательности")
public class CreateRequest {
    @Schema(description = "Наименование достопримечательности", example = "Эрмитаж")
    @Size(min = 3, max = 50, message = "Наименование достопримечательности должно содержать от 3 до 50 символов")
    @NotBlank(message = "Наименование достопримечательности не может быть пустым")
    private String name;

    @Schema(description = "Краткое описание", example = "Example description")
    @Size(min = 5, max = 200, message = "Краткое описание должно содержать от 5 до 200 символов")
    @NotBlank(message = "Краткое описание не может быть пустым")
    private String briefDescription;

    private TypeAttraction typeAttraction;

    private Locality locality;
}
