package br.com.backend.ecommerce.mapper;

import br.com.backend.ecommerce.dto.request.EnderecoRequest;
import br.com.backend.ecommerce.dto.response.EnderecoResponse;
import br.com.backend.ecommerce.model.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    Endereco toEntity(EnderecoRequest request);
    EnderecoResponse toRes(Endereco endereco);
}
