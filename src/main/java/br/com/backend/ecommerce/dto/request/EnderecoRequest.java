package br.com.backend.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

public record EnderecoRequest (@NotNull(message = "É obrigatório") String cep,
                               String rua,
                               String bairro,
                               String estado,
                               String cidade,
                               String complemento,
                               String numeroCasa){
}
