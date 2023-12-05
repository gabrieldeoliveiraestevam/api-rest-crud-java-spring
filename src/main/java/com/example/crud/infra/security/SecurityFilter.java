package com.example.crud.infra.security;

import com.example.crud.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// A cada requisição ele vai fazer essa validação com o token enviado
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        // Caso o token exista
        if( token != null ){
            var login = tokenService.validateToken(token);
            UserDetails user = this.repository.findByLogin(login);

            // Autentica o usuário
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            // Passa para a próxima requisição o usuário encontrado e autenticado
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        // Verifica se o token foi enviado
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            return null;
        }
        // Retirar o Bearer do token
        return authHeader.replace("Bearer ", "");
    }
}
