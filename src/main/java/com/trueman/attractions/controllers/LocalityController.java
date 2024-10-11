package com.trueman.attractions.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trueman.attractions.dto.locality.CreateRequest;
import com.trueman.attractions.services.LocalityService;
import com.trueman.attractions.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер местоположений.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/locality")
public class LocalityController {
    /**
     * Внедрение зависимости для бизнес-логики.
     */
    private final LocalityService localityService;
    private final WeatherService weatherService;
    /**
     * Метод получения списка существующих местоположений.
     */
    @Operation(summary = "Получение списка местоположений")
    @GetMapping("/read")
    public ResponseEntity<?> readLocality() {

        return localityService.getListLocality();
    }

    /**
     * Метод создания нового местоположения.
     */
    @Operation(summary = "Создание нового местоположения")
    @PostMapping("/create")
    public ResponseEntity<String> createLocality(@RequestBody @Valid CreateRequest createRequest,
                                                 BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(".\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        return localityService.createLocality(createRequest);
    }

    /**
     * Метод удаления местоположения.
     */
    @Operation(summary = "Удаление местоположения по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLocality(@PathVariable("id") Long id) {
        return localityService.deleteLocality(id);
    }

    @Operation(summary = "Получение данных о погоде в определённом местоположении")
    @GetMapping("/weather")
    public ResponseEntity<?> getWeather(@RequestParam double latitude, @RequestParam double longitude) throws JsonProcessingException {
        return weatherService.getCurrentWeather(latitude, longitude);
    }
}
