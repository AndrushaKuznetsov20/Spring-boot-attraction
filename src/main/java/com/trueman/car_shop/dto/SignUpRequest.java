package com.trueman.car_shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {
    @Schema(description = "Имя пользователя", example = "Andrey")
    @Size(min = 5, max = 30, message = "Имя пользователя должно содержать от 5 до 30 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "andrey@mail.ru")
    @Size(min = 5, max = 50, message = "Адрес электронной почты должен содержать от 5 до 50 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате user@example.ru")
    private String email;

    @Schema(description = "Номер телефона", example = "89209007070")
    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(min = 11, max = 11, message = "Длина номера телефона должна быть равна 11 символам")
    private String number;

    @Schema(description = "Пароль", example = "Password_Example22_")
    @Size(max = 50, message = "Длина пароля должна быть не более 50 символов")
    private String password;
}
