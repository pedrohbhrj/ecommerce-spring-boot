package br.com.pedrohbhrj.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @Email(message = "Format invalid.")
        String email,
        @NotBlank(message = "Should not be empty.")
        String password) {
}
