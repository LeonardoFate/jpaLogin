package com.anthony.login.jpaLogin.service.impl;

import com.anthony.login.jpaLogin.entity.SecurityUser;
import com.anthony.login.jpaLogin.entity.User;
import com.anthony.login.jpaLogin.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("Usuario encontrado: " + user.getUsername()); // Agrega esta línea para depuración
        return new SecurityUser(user);
    }

}
