package br.com.backend.ecommerce.dto.response;

import java.util.UUID;

public record EnderecoResponse (UUID id,
                                String cep,
                                String rua,
                                String estado,
                                String cidade,
                                String bairro,
                                String complemento,
                                String numeroCasa,
                                boolean ativo,
                                boolean principal){
}
