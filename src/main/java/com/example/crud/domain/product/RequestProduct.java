package com.example.crud.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO = record = classe somente com atributos
public record RequestProduct(
        @NotBlank String name,
        @NotNull Integer price_in_cents
) {}
