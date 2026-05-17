package br.com.pedrohbhrj.controllers.docs;

import br.com.pedrohbhrj.DTO.request.LoginRequest;
import br.com.pedrohbhrj.DTO.request.RegisterUserRequest;
import br.com.pedrohbhrj.DTO.response.LoginResponse;
import br.com.pedrohbhrj.DTO.response.RegisterUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticação", description = "Registrar e logar usuarios.")
public interface AuthDocs {

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Registrar novo usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
    })
    ResponseEntity<RegisterUserResponse> registerUser(RegisterUserRequest request);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Logar usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario logado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    })
    ResponseEntity<LoginResponse> login(LoginRequest request);
}
