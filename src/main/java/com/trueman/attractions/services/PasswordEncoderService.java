package com.trueman.attractions.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервис, представляющий метод для кодирования пароля пользователя.
 */
@Service
public class PasswordEncoderService {
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
