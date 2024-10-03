package com.trueman.attractions.dto.assistance;

import com.trueman.attractions.models.enums.TypeAssistance;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание услуги")
public class CreateRequest {

    private TypeAssistance typeAssistance;

    @Schema(description = "Краткое описание", example = "Пример описания")
    @Size(min = 3, max = 200, message = "Краткое описание должно содержать от 3 до 200 символов")
    @NotBlank(message = "Краткое описание не может быть пустым")
    private String briefDescription;

    @Schema(description = "Исполнитель", example = "Исполнитель")
    @Size(min = 3, max = 50, message = "Наименование исполнителя должно содержать от 3 до 50 символов")
    @NotBlank(message = "Наименование исполнителя не может быть пустым")
    private String performer;
}
