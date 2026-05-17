package br.com.pedrohbhrj.controllers.docs;

import br.com.pedrohbhrj.DTO.request.UserUpdateRequest;
import br.com.pedrohbhrj.DTO.response.UserResponse;
import br.com.pedrohbhrj.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Users", description = "Operações relacionadas ao usuario.")
public interface UserDocs {

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Atualizar usuario autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado."),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado.")
    })
    ResponseEntity<UserResponse> updateUser(User user, UserUpdateRequest request);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Encontrar usuario autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Não autenticado."),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado.")
    })
    ResponseEntity<UserResponse> findUser(User user);
}
