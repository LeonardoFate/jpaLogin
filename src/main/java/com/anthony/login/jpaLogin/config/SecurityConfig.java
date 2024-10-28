package com.anthony.login.jpaLogin.config;

import com.anthony.login.jpaLogin.service.impl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailServiceImpl;  // Inyecta tu UserDetailServiceImpl

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()  // Rutas públicas
                        .requestMatchers("/v1/home").authenticated()  // Autenticación necesaria
                        .requestMatchers("/v1/admin").hasRole("ADMIN")  // Solo ADMIN puede acceder
                        .anyRequest().authenticated())  // Otras rutas requieren autenticación
                .formLogin(Customizer.withDefaults())  // Login por defecto
                .build();
    }

    // Configura el PasswordEncoder (BCrypt en este caso)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración de AuthenticationManager para usar tu UserDetailServiceImpl
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailServiceImpl)  // Usa tu UserDetailServiceImpl
                .passwordEncoder(passwordEncoder())  // Usa BCrypt para codificación de contraseñas
                .and()
                .build();
    }
}
