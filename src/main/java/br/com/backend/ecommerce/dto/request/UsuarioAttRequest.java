package br.com.backend.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UsuarioAttRequest(@CPF(message = "Formato inválido.") String cpf,
                                String nomeCompleto,
                                LocalDate dataNascimento,
                                @NotNull @Email(message = "Formato inválido.") String email
                                ) {
}
