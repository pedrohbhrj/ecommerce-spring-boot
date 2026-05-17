package br.com.pedrohbhrj.controllers;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;
import br.com.pedrohbhrj.controllers.docs.PaymentDocs;
import br.com.pedrohbhrj.services.interf.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController implements PaymentDocs {

    private final PaymentService paymentService;

    @PutMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> processPayment(@PathVariable Long orderId){
        return ResponseEntity.ok(paymentService.processPayment(orderId));
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> findPayment(@PathVariable("orderId") Long orderId){
        return ResponseEntity.ok(paymentService.findPaymentById(orderId));
    }
}
