package com.trueman.attractions.services;

import com.trueman.attractions.dto.JwtAuthenticationResponse;
import com.trueman.attractions.dto.SignInRequest;
import com.trueman.attractions.dto.SignUpRequest;
import com.trueman.attractions.models.User;
import com.trueman.attractions.models.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoderService passwordEncoderService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request, String selectedRole) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setNumber(request.getNumber());
        user.setPassword(passwordEncoderService.passwordEncoder().encode(request.getPassword()));
        user.setActive(true);

        if(selectedRole.equals("SIMPLE_USER"))
        {
            user.setRole(Role.SIMPLE_USER);
        }
        else {
            user.setRole(Role.MODERATOR);
        }

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        System.out.println(jwt);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
