package br.com.pedrohbhrj.services.impl;


import br.com.pedrohbhrj.DTO.request.ProductRequest;
import br.com.pedrohbhrj.DTO.request.ProductUpdateRequest;
import br.com.pedrohbhrj.DTO.response.ProductResponse;
import br.com.pedrohbhrj.exceptions.NotFoundException;
import br.com.pedrohbhrj.mapper.ProductMapper;
import br.com.pedrohbhrj.models.Category;
import br.com.pedrohbhrj.models.Product;
import br.com.pedrohbhrj.repository.CategoryRepository;
import br.com.pedrohbhrj.repository.ProductRepository;
import br.com.pedrohbhrj.services.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {


        Optional<Category> categoryOpt = categoryRepository.findByNameIgnoreCase(request.categoryName());

        Product product = productMapper.toEntity(request);

        if (categoryOpt.isEmpty()) {
            Category category = new Category();
            category.setName(request.categoryName());
            Category saved = categoryRepository.save(category);

            product.setCategory(saved);
        }

        categoryOpt.ifPresent(product::setCategory);


        Product productSaved = productRepository.save(product);

        log.info("Product created successfully, with name: {}", productSaved.getName());


        return productMapper.toResponse(productSaved);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        productMapper.mergeProduct(request, product);

        Product productSaved = productRepository.save(product);

        log.info("Product updated successfully, with name: {}", productSaved.getName());

        return productMapper.toResponse(productSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findProductById(Long productId) {

        Product productFound = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        log.info("Product was found, with name: {}", productFound.getName());

        return productMapper.toResponse(productFound);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<ProductResponse> productPage = productRepository.findAll(pageRequest).map(productMapper::toResponse);

        log.info("Products paginated successfully");

        return productPage;
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {

        Product productFound = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        productRepository.delete(productFound);

        log.info("Product deleted successfully");
    }
}
