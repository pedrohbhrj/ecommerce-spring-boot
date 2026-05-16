package br.com.pedrohbhrj.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @Size(min = 2, max = 120, message = "Nome deve ter entre 2 e 120 caracteres")
        String name,
        @Email(message = "Formato de inválido.")
        String email,
        @Size(min = 8, message = "Senha deve ter no minimo oito caracteres.")
        String password) {
}
