package com.trueman.attractions.services;

import com.trueman.attractions.models.User;
import com.trueman.attractions.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис, который содержит бизнес-логику для управления пользователями
 */
@Service
@RequiredArgsConstructor
public class UserService {
    /**
     * Внедрение репозитория для взаимодействия с базой данных
     */
    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;

    /**
     * Внедрение создания нового пользователя.
     */
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким логином уже существует!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует!");
        }

        return save(user);
    }

    /**
     * Метод сохранения пользователя в БД.
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Метод поиска пользователя по имени.
     */
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден !"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Метод получения текущего пользователя.
     */
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    /**
     * Метод поиска пользователя по ID.
     */
    public ResponseEntity<User> findByUserId(Long userId)
    {
        User user = userRepository.findById(userId).orElse(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
