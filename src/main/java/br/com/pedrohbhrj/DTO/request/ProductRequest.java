package br.com.pedrohbhrj.DTO.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest (@NotBlank(message = "Should not be empty") @Size(max = 120,message = "Max 120 characters") @NotNull
                              String name,
                              @Size(max = 300,message = "Max 300 characters")
                              String description,
                              @NotNull
                              @Positive(message = "Must be a positive number")
                              BigDecimal price,
                              @NotNull
                              @Min(value = 1,message = "Must be a positive number")
                              Integer stockQuantity,
                              @NotBlank(message = "Should not be empty")
                              String categoryName,
                              String imgUrl){
}
