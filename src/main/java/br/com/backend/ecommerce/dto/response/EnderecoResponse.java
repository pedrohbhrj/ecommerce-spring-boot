package br.com.backend.ecommerce.dto.response;

public record EnderecoResponse (String cep,
                                String rua,
                                String estado,
                                String cidade,
                                String bairro,
                                String complemento,
                                String numeroCasa){
}
