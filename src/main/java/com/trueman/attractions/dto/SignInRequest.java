package com.trueman.attractions.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {
    @Schema(description = "Имя пользователя", example = "Andrey")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Пароль", example = "Password_Example22_")
    @Size(max = 50, message = "Длина пароля должна быть не более 50 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
