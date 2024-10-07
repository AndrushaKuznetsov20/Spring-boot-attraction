package com.trueman.attractions.controllers;

import com.trueman.attractions.dto.assistance.CreateRequest;
import com.trueman.attractions.services.AssistanceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 * Контроллер услуг.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assistance")
public class AssistanceController {
    /**
     * Внедрение сервиса для бизнес-логики.
     */
    private final AssistanceService assistanceService;

    /**
     * Метод получения списка существующих услуг.
     */
    @Operation(summary = "Получение списка услуг")
    @GetMapping("/read")
    public ResponseEntity<?> readAssistance() throws Exception{

        return assistanceService.getListAssistance();
    }

    /**
     * Метод создания новой услуги.
     */
    @Operation(summary = "Создание новой услуги")
    @PostMapping("/create")
    public ResponseEntity<String> createAssistance(@RequestBody @Valid CreateRequest createRequest,
                                                   BindingResult bindingResult) throws Exception{

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(".\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        return assistanceService.createAssistance(createRequest);
    }

    @Operation(summary = "Добавление достопримечательности")
    @PostMapping("/addAttraction/{attractionId}/{assistanceId}")
    public ResponseEntity<String> addAttraction(@PathVariable("attractionId") Long attractionId,
    @PathVariable("assistanceId") Long assistanceId) throws Exception{
        return assistanceService.addAttractionByListAssistance(attractionId, assistanceId);
    }

    /**
     * Метод удаления услуги.
     */
    @Operation(summary = "Удаление услуги по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAssistance(@PathVariable("id") Long id) throws Exception{
        return assistanceService.deleteAssistance(id);
    }
}
