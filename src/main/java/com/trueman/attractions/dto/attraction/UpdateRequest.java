package com.trueman.attractions.dto.attraction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на изменение краткого описания достопримечательности")
public class UpdateRequest {
    @Schema(description = "Краткое описание", example = "Example description")
    @Size(min = 5, max = 200, message = "Краткое описание должно содержать от 5 до 200 символов")
    @NotBlank(message = "Краткое описание не может быть пустым")
    private String briefDescription;
}
