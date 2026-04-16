package br.com.backend.ecommerce.dto.response;

import java.util.UUID;

public record EnderecoResponse (UUID id,
                                String tipoEndereco,
                                String cep,
                                String rua,
                                String estado,
                                String cidade,
                                String bairro,
                                String complemento,
                                String numeroCasa){
}
