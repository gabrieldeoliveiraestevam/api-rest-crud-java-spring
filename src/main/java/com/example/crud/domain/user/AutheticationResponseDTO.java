package com.example.crud.domain.user;

import jakarta.validation.constraints.NotNull;

public record AutheticationResponseDTO(
        String token
) { }
