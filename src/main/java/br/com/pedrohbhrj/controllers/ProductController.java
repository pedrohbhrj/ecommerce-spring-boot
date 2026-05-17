package br.com.pedrohbhrj.controllers;


import br.com.pedrohbhrj.DTO.request.ProductRequest;
import br.com.pedrohbhrj.DTO.request.ProductUpdateRequest;
import br.com.pedrohbhrj.DTO.response.ProductResponse;
import br.com.pedrohbhrj.controllers.docs.ProductDocs;
import br.com.pedrohbhrj.services.interf.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController implements ProductDocs {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> findProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") Long productId,@RequestBody @Valid ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productId,request));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> findProducts(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.findAll(page, size));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {

        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }
}
