package br.com.pedrohbhrj.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRequest(@NotBlank(message = "Should not be empty") @NotNull String street,
                             @NotBlank(message = "Should not be empty") @NotNull String number,
                             @NotBlank(message = "Should not be empty") @NotNull String zipCode,
                             String complement,
                             @NotBlank(message = "Should not be empty") @NotNull String neighborhood,
                             @NotBlank(message = "Should not be empty") @NotNull String city,
                             @NotBlank(message = "Should not be empty") @NotNull String state) {
}
