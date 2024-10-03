package com.trueman.attractions.dto.locality;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание местоположения")
public class CreateRequest {
    @Schema(description = "Населённый пункт", example = "Москва")
    @Size(min = 5, max = 50, message = "Населённый пункт должен содержать от 5 до 50 символов")
    @NotBlank(message = "Населённый пункт не может быть пустым")
    private String settlement;

    @Schema(description = "Регион", example = "Владимирская область 33 регион")
    @Size(min = 5, max = 50, message = "Регион должен содержать от 5 до 50 символов")
    @NotBlank(message = "Регион не может быть пустым")
    private String region;

    @NotNull(message = "Значение широты не может быть пустым")
    private double latitude;

    @NotNull(message = "Значение долготы не может быть пустым")
    private double longitude;
}
