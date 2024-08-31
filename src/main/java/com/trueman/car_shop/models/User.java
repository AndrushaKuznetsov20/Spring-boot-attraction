package com.trueman.car_shop.models;


import com.trueman.car_shop.models.enums.Role;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Description("Идентификатор пользователя")
    @Column(name = "id")
    private Long id;

    @Description("Логин пользователя")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Description("Электронная почта пользователя")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Description("Номер телефона пользователя")
    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @Description("Пароль")
    @Column(name = "password", nullable = false)
    private String password;

    @Description("Активность пользователя")
    @Column(name = "active")
    private boolean active;

    @Description("Роль пользователя")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return active;
    }

}
