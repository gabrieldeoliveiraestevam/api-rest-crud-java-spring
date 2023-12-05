package com.example.crud.infra.error;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Indica que essa classe lida com exceções. O spring vai acionar ela.
// Indica as exceções que ela vai tratar.
@ControllerAdvice
public class RequestExceptionHandler {
    // Toda vez que essa exceção for lançada, chama esse método que ele vai tratar o retorno para o cliente
    // Evita que seja enviados muitos dados para o cliente. Evitando assim expor o app.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(){
        var response = new ExceptionDTO("Dado não encontado");
        return ResponseEntity.badRequest().body("Dado não encontrado");
    }

}
