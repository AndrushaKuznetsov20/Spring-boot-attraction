package com.trueman.attractions.services;

import com.trueman.attractions.dto.JwtAuthenticationResponse;
import com.trueman.attractions.dto.SignInRequest;
import com.trueman.attractions.dto.SignUpRequest;
import com.trueman.attractions.models.User;
import com.trueman.attractions.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsValidAuthService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

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
