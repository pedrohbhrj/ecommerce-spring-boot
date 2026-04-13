package br.com.backend.ecommerce.dto.request;

import br.com.backend.ecommerce.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthRequest (@Email(message = "Formato inválido.") String email,
                           @NotNull(message = "É obrigatório.") String senha,
                           Role role){
}
