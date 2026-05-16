package br.com.pedrohbhrj.mapper;

import br.com.pedrohbhrj.DTO.request.ProductRequest;
import br.com.pedrohbhrj.DTO.request.ProductUpdateRequest;
import br.com.pedrohbhrj.DTO.response.ProductResponse;
import br.com.pedrohbhrj.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "category.name",source = "categoryName")
    Product toEntity(ProductRequest request);

    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "category",ignore = true)
    void mergeProduct(ProductUpdateRequest request,@MappingTarget Product product);

    @Mapping(source = "category.name",target = "categoryName")
    ProductResponse toResponse(Product product);

}
