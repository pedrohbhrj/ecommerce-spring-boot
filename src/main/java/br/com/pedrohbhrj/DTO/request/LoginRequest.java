package br.com.pedrohbhrj.DTO.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "Format invalid") String email,
        @NotBlank(message = "Should not be empty") String password) {
}
