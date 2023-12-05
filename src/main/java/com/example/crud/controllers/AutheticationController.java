package com.example.crud.controllers;

import com.example.crud.domain.user.AutheticationDTO;
import com.example.crud.domain.user.RegisterDTO;
import com.example.crud.domain.user.User;
import com.example.crud.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AutheticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutheticationDTO data) {
        // Junta login e senha e forma um token
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        // Realiza a autenticação - Compara com hash com o banco de dados
        var auth = this.authenticationManager.authenticate(userNamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        // Caso o usuário exista no banco de dados. Retorna erro
        if (this.repository.findByLogin(data.login()) != null){
            return ResponseEntity.badRequest().body("Login já existe");
        }

        // Cria a hash da password
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        // Cria a instância e salva no banco de dados
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();

    }
}
