package br.com.backend.ecommerce.mapper;

import br.com.backend.ecommerce.dto.request.EnderecoRequest;
import br.com.backend.ecommerce.dto.response.EnderecoResponse;
import br.com.backend.ecommerce.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "id",ignore = true)
    Endereco toEntity(EnderecoRequest request);
    @Mapping(target = "tipoEndereco",source = "tipoDaChave")
    EnderecoResponse toRes(Endereco endereco, String tipoDaChave);

}
