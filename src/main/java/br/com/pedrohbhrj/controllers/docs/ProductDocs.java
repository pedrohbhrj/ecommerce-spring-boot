package br.com.pedrohbhrj.controllers.docs;

import br.com.pedrohbhrj.DTO.request.ProductRequest;
import br.com.pedrohbhrj.DTO.request.ProductUpdateRequest;
import br.com.pedrohbhrj.DTO.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Produtos", description = "Admin pode criar ,atualizar e deletar produtos e todos podem ver os produtos e encontrar um produto pelo id.")
public interface ProductDocs {
    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Criar novo produto.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    ResponseEntity<ProductResponse> createProduct(ProductRequest request);

    @Operation(summary = "Encontrar produto por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    })
    ResponseEntity<ProductResponse> findProduct(Long productId);

    @Operation(summary = "Encontrar todos os produtos paginados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso.")
    })
    ResponseEntity<Page<ProductResponse>> findProducts(int page, int size);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Atualizar produto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos atualizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    })
    ResponseEntity<ProductResponse> updateProduct(Long productId, ProductUpdateRequest request);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Deletar produto.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId);
}
