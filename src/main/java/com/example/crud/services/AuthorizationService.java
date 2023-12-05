package com.example.crud.services;

import com.example.crud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Implementa a interface UserDetailsService do Spring Security
// Spring Security implementa métodos padrões
// Serviço de autorização de usuário que faz requisição com o app
@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    // Carrega o usuário que fez a requisição
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByLogin(username);
    }
}
