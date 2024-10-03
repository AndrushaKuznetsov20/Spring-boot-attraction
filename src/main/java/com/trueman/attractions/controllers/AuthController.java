package com.trueman.attractions.controllers;

import com.trueman.attractions.dto.auth.SignInRequest;
import com.trueman.attractions.dto.auth.SignUpRequest;
import com.trueman.attractions.services.AuthenticationService;
import com.trueman.attractions.services.IsValidAuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final IsValidAuthService isValidAuthService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up/{selectedRole}")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request,
                                    BindingResult bindingResult, @PathVariable("selectedRole") String selectedRole) {

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(".\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        return isValidAuthService.isValidRegister(request, selectedRole);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(".\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        return isValidAuthService.isValidLogin(request);
    }
}
