package com.example.crud.domain.user;

import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotNull
        String login,
        @NotNull
        String password,
        @NotNull
        UserRole role
) { }
