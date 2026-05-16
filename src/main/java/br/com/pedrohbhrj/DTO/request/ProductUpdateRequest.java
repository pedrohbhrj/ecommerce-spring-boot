package br.com.pedrohbhrj.DTO.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        @NotBlank(message = "Should not be empty") @Size(max = 120, message = "Max 120 characters")
        String name,
        @Size(max = 300, message = "Max 300 characters")
        String description,
        @Positive(message = "Must be a positive number")
        BigDecimal price,
        @Min(value = 1, message = "Must be a positive number")
        Integer stockQuantity,
        @NotBlank(message = "Should not be empty")
        String categoryName,
        String imgUrl) {
}
