package br.com.pedrohbhrj.controllers.docs;

import br.com.pedrohbhrj.DTO.request.AddressRequest;
import br.com.pedrohbhrj.DTO.request.AddressUpdateRequest;
import br.com.pedrohbhrj.DTO.response.AddressResponse;
import br.com.pedrohbhrj.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Endereço", description = "Usuario crud de endereço.")
public interface AddressDocs {

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Criar um novo endereço.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado.")
    })
    ResponseEntity<AddressResponse> createAddress(User user, AddressRequest request);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Encontrar endereço pelo id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    ResponseEntity<AddressResponse> getAddressById(User user,Long addressId);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Atualizar endereço.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    ResponseEntity<AddressResponse> updateAddress(User user,Long addressId, AddressUpdateRequest request);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Deletar endereço.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    ResponseEntity<Void> deleteAddress(User user,Long addressId);
}
