package br.com.backend.ecommerce.mapper;


import br.com.backend.ecommerce.dto.request.UsuarioAttRequest;
import br.com.backend.ecommerce.dto.response.UsuarioResponse;
import br.com.backend.ecommerce.model.Usuario;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeUsuario(@MappingTarget Usuario usuario, UsuarioAttRequest request);

    UsuarioResponse toRes(Usuario usuario);
}
