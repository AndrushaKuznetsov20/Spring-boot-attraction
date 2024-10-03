package com.trueman.attractions.controllers;

import com.trueman.attractions.dto.locality.CreateRequest;
import com.trueman.attractions.dto.locality.ListResponse;
import com.trueman.attractions.services.LocalityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locality")
public class LocalityController {
    private final LocalityService localityService;

    @Operation(summary = "Получение списка местоположений")
    @GetMapping("/read")
    public ResponseEntity<ListResponse> readLocality() {

        return localityService.getListLocality();
    }

    @Operation(summary = "Создание нового местоположения")
    @PostMapping("/create")
    public ResponseEntity<String> createLocality(@RequestBody @Valid CreateRequest createRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(".\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        return localityService.createLocality(createRequest);
    }

    @Operation(summary = "Удаление местоположения по ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLocality(@PathVariable("id") Long id) {
        return localityService.deleteLocality(id);
    }
}
