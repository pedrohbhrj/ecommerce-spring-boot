package br.com.pedrohbhrj.controllers.docs;

import br.com.pedrohbhrj.DTO.request.CategoryRequest;
import br.com.pedrohbhrj.DTO.request.CategoryUpdateRequest;
import br.com.pedrohbhrj.DTO.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Categorias", description = "Admin cria , atualiza e deleta categorias, todos podem ver as categorias e encontrar por id.")
public interface CategoryDocs {

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Criar nova categoria.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou categoria existente ao criar nova."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    ResponseEntity<CategoryResponse> createCategory(CategoryRequest request);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Encontrar categoria por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<CategoryResponse> findByCategoryId(Long categoryId);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Encontrar todas categorias.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categorias encontradas com sucesso.")
    })
    ResponseEntity<List<CategoryResponse>> findAllCategories();

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Atualizar categoria.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    ResponseEntity<CategoryResponse> updateCategory(Long categoryId, CategoryUpdateRequest request);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Deletar categoria.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    ResponseEntity<Void> deleteCategory(Long categoryId);
}
