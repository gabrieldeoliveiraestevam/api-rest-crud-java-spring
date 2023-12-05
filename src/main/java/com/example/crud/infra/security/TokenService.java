package com.example.crud.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.crud.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

// Controla os Tokens gerados pela JTW
@Service
public class TokenService {
    // Busca variáveis definidas no arquivo de configuração - Chave de criptografia
    @Value("${api.security.token.secret}")
    private String secretKey;

    // Metodo para gerar o token
    public String generateToken(User user){
        try{
            // Define o algoritmo de criptografia e a chave secreta da api
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            // Cria o token com base no nome da api, login do usuário, expiração e algoritimo
            String token = JWT.create()
                    .withIssuer("api-rest-crud-java-sping.api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro na geração do token ", exception);
        }
    }

    // Metodo para validar o token
    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            // Utiliza o algoritmo para fazer o processo de descriptar.
            // Verifica se o token foi gerado por esse algoritmo
            // Retorna o login incluido no token
            String login = JWT.require(algorithm)
                    .withIssuer("api-rest-crud-java-sping.api")
                    .build()
                    .verify(token)
                    .getSubject();
            return login;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Token invalido ", exception);
        }
    }
    // Define o tempo de expiração do token de 2 horas
    private Instant getExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
