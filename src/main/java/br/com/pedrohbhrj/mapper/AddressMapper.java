package br.com.pedrohbhrj.mapper;

import br.com.pedrohbhrj.DTO.request.AddressRequest;
import br.com.pedrohbhrj.DTO.request.AddressUpdateRequest;
import br.com.pedrohbhrj.DTO.response.AddressResponse;
import br.com.pedrohbhrj.models.Address;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AddressMapper {

    AddressResponse toResponse(Address address);


    Address toEntity(AddressRequest request);

    void mergeAddress(AddressUpdateRequest request,@MappingTarget Address address);
}
