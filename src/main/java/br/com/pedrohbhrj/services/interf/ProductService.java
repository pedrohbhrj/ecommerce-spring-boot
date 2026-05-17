package br.com.pedrohbhrj.services.interf;

import br.com.pedrohbhrj.DTO.request.ProductRequest;
import br.com.pedrohbhrj.DTO.request.ProductUpdateRequest;
import br.com.pedrohbhrj.DTO.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long productId, ProductUpdateRequest request);

    ProductResponse findProductById(Long productId);

    Page<ProductResponse> findAll(int page, int size);

    void deleteProduct(Long productId);
}
