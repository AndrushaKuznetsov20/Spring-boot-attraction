package com.trueman.car_shop.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService {
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
