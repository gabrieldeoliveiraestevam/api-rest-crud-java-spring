package com.example.crud.domain.user;

import jakarta.validation.constraints.NotNull;

public record AutheticationDTO(
        @NotNull
        String login,
        @NotNull
        String password
) { }
