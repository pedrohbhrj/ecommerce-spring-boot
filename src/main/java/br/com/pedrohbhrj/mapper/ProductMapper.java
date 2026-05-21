package br.com.pedrohbhrj.mapper;

import br.com.pedrohbhrj.DTO.request.ProductRequest;
import br.com.pedrohbhrj.DTO.request.ProductUpdateRequest;
import br.com.pedrohbhrj.DTO.response.ProductResponse;
import br.com.pedrohbhrj.models.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {


    @Mapping(target = "category.name",source = "categoryName")
    Product toEntity(ProductRequest request);

    void mergeProduct(ProductUpdateRequest request,@MappingTarget Product product);

    @Mapping(source = "category.name",target = "categoryName")
    ProductResponse toResponse(Product product);

}
