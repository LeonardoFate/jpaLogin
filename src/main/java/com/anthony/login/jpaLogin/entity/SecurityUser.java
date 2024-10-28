package com.anthony.login.jpaLogin.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
public class SecurityUser implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Aquí puedes añadir lógica para determinar si la cuenta ha expirado
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Aquí puedes añadir lógica para determinar si la cuenta está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Aquí puedes añadir lógica para determinar si las credenciales han expirado
    }

    @Override
    public boolean isEnabled() {
        return true; // Aquí puedes añadir lógica para determinar si la cuenta está habilitada
    }
}
