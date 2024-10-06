package com.trueman.attractions.controllers;

import com.trueman.attractions.dto.attraction.CreateRequest;
import com.trueman.attractions.dto.attraction.UpdateRequest;
import com.trueman.attractions.models.enums.TypeAttraction;
import com.trueman.attractions.services.AttractionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер достопримечательностей.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/attraction")
public class AttractionController {
    /**
     * Внедрение сервиса для бизнес-логики.
     */
    private final AttractionService attractionService;

    /**
     * Метод получения списка существующих достопримечательностей.
     */
    @Operation(summary = "Получение списка достопримечательностей")
    @GetMapping("/read")
    public ResponseEntity<?> readAttraction(@RequestParam(required = false) TypeAttraction typeAttraction,
                                            @RequestParam(required = false) Long localityId) throws Exception{

        return attractionService.getListAttraction(typeAttraction, localityId);
    }

    /**
     * Метод создания новой достопримечательности.
     */
    @Operation(summary = "Создание новой достопримечательности")
    @PostMapping("/create")
    public ResponseEntity<String> createAttraction(@RequestBody @Valid CreateRequest createRequest,
                                                   BindingResult bindingResult) throws Exception{

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(".\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        return attractionService.createAttraction(createRequest);
    }

    /**
     * Метод обновления достопримечательностей.
     */
    @Operation(summary = "Обновление достопримечательности по ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAttraction(@PathVariable("id") Long id, @RequestBody @Valid
    UpdateRequest updateRequest, BindingResult bindingResult) throws Exception{

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(".\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        return attractionService.updateAttraction(updateRequest, id);
    }

    /**
     * Метод удаления достопримечательности.
     */
    @Operation(summary = "Удаление достопримечательности по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAttraction(@PathVariable("id") Long id) throws Exception{
        return attractionService.deleteAttraction(id);
    }
}
