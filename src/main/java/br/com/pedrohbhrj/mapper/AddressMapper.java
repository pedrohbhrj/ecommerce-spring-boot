package br.com.pedrohbhrj.mapper;

import br.com.pedrohbhrj.DTO.request.AddressRequest;
import br.com.pedrohbhrj.DTO.request.AddressUpdateRequest;
import br.com.pedrohbhrj.DTO.response.AddressResponse;
import br.com.pedrohbhrj.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressResponse toResponse(Address address);

    @Mapping(target = "user",ignore = true)
    @Mapping(target = "id",ignore = true)
    Address toEntity(AddressRequest request);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "user",ignore = true)
    void mergeAddress(AddressUpdateRequest request,@MappingTarget Address address);
}
