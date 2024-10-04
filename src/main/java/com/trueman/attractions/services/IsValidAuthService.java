package com.trueman.attractions.services;

import com.trueman.attractions.dto.auth.JwtAuthenticationResponse;
import com.trueman.attractions.dto.auth.SignInRequest;
import com.trueman.attractions.dto.auth.SignUpRequest;
import com.trueman.attractions.models.User;
import com.trueman.attractions.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Сервис валидации данных при аутентификации.
 */
@Service
@RequiredArgsConstructor
public class IsValidAuthService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    /**
     * Валидация данных при авторизации.
     */
    public ResponseEntity<?> isValidLogin(SignInRequest signInRequest)
    {
        User existingUser = userRepository.findByNameIsValidAuth(signInRequest.getUsername());

        if(existingUser != null)
        {
            if(existingUser.isActive())
            {
                JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);
                return ResponseEntity.ok(response);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.LOCKED);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Валидация данных при регистрации.
     */
    public ResponseEntity<?> isValidRegister(SignUpRequest signUpRequest, String selectedRole)
    {
        if(!userRepository.existsByUsername(signUpRequest.getUsername()))
        {
            if(!userRepository.existsByEmail(signUpRequest.getEmail()))
            {
                if(!userRepository.existsByNumber(signUpRequest.getNumber()))
                {
                    authenticationService.signUp(signUpRequest, selectedRole);
                    return ResponseEntity.ok("Регистрация прошла успешно !");
                }
                else
                {
                    return ResponseEntity.ok("Пользователь с номером" + " " + signUpRequest.getNumber() + " " + "уже существует !");
                }
            }
            else
            {
                return ResponseEntity.ok("Пользователь с Email" + " " + signUpRequest.getEmail() + " " + "уже существует !");
            }
        }
        else
        {
            return ResponseEntity.ok("Пользователь с логином" + " " + signUpRequest.getUsername() + " " + "уже существует");
        }
    }
}
