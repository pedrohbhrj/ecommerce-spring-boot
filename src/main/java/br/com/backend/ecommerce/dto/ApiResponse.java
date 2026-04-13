package br.com.backend.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (String mensagem,
                           int status,
                           T data){
}
