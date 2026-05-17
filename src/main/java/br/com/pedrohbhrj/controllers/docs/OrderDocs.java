package br.com.pedrohbhrj.controllers.docs;

import br.com.pedrohbhrj.DTO.request.OrderRequest;
import br.com.pedrohbhrj.DTO.response.OrderResponse;
import br.com.pedrohbhrj.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Pedidos", description = "Criação de pedidos e encontrando pedidos do propio usuario autenticado.")
public interface OrderDocs {
    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Criar pedido.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado")
    })
    ResponseEntity<Map<String, String>> createOrder(User user, OrderRequest request);

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Encontrar pedidos paginados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado"),
    })
    ResponseEntity<Page<OrderResponse>> myOrders(
            int page,
            int size,
            User user
    );

    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Encontrar pedido por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuario não autenticado"),
    })
    ResponseEntity<OrderResponse> myOrder(User user,Long orderId);
}
