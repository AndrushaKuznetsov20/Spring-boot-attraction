package com.trueman.attractions.controllers;

import com.trueman.attractions.dto.assistance.CreateRequest;
import com.trueman.attractions.dto.assistance.ListResponse;
import com.trueman.attractions.services.AssistanceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assistance")
public class AssistanceController {
    private final AssistanceService assistanceService;

    @Operation(summary = "Получение списка услуг")
    @GetMapping("/read")
    public ResponseEntity<ListResponse> readAssistance() {

        return assistanceService.getListAssistance();
    }

    @Operation(summary = "Создание новой услуги")
    @PostMapping("/create")
    public ResponseEntity<String> createAssistance(@RequestBody @Valid CreateRequest createRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(".\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        return assistanceService.createAssistance(createRequest);
    }

    @Operation(summary = "Удаление услуги по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAssistance(@PathVariable("id") Long id) {
        return assistanceService.deleteAssistance(id);
    }
}
