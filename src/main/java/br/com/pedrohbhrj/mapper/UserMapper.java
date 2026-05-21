package br.com.pedrohbhrj.mapper;

import br.com.pedrohbhrj.DTO.request.UserUpdateRequest;
import br.com.pedrohbhrj.models.User;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    void mergeUser(UserUpdateRequest request,@MappingTarget User user);
}
