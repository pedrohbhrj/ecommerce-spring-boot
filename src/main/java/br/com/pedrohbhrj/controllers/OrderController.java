package br.com.pedrohbhrj.controllers;

import br.com.pedrohbhrj.DTO.request.OrderRequest;
import br.com.pedrohbhrj.DTO.response.OrderResponse;
import br.com.pedrohbhrj.controllers.docs.OrderDocs;
import br.com.pedrohbhrj.models.User;
import br.com.pedrohbhrj.services.interf.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController implements OrderDocs {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createOrder(@AuthenticationPrincipal User user, @RequestBody @Valid OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(user, request));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> myOrders(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(orderService.findAllOrders(page, size, user));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> myOrder(@AuthenticationPrincipal User user,@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.findOrderById(user,orderId));
    }

}
