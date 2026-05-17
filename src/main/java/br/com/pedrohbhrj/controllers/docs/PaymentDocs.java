package br.com.pedrohbhrj.controllers.docs;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pagamentos", description = "Atualização do pagamento e encontrando pagamento do propio usuario autenticado.")
public interface PaymentDocs {
    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Processar pagamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento processado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<PaymentResponse> processPayment(Long orderId);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Encontrar pagamento por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado ou pedido não encontrado."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado")
    })
    ResponseEntity<PaymentResponse> findPayment(Long orderId);
}
