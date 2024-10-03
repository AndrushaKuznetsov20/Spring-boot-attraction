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
@Schema(description = "Запрос на создание достопримечательности")
public class CreateRequest {
    @Schema(description = "Наименование достопримечательности", example = "Эрмитаж")
    @Size(min = 3, max = 50, message = "Наименование достопримечательности должно содержать от 3 до 50 символов")
    @NotBlank(message = "Наименование достопримечательности не может быть пустым")
    private String name;

    @NotBlank(message = "Дата создания не может быть пустым")
    private LocalDate createDate;

    @Schema(description = "Краткое описание", example = "Example description")
    @Size(min = 5, max = 200, message = "Краткое описание должно содержать от 5 до 200 символов")
    @NotBlank(message = "Краткое описание не может быть пустым")
    private String briefDescription;

    @NotBlank(message = "Тип достопримечательности не может быть пустым")
    private TypeAttraction typeAttraction;

    @NotBlank(message = "Местоположение не может быть пустым")
    private Locality locality;

    @NotBlank(message = "Местоположение не может быть пустым")
    private List<Assistance> assistanceList;
}
