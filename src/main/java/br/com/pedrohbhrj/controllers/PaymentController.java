package br.com.pedrohbhrj.controllers;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;
import br.com.pedrohbhrj.controllers.docs.PaymentDocs;
import br.com.pedrohbhrj.models.User;
import br.com.pedrohbhrj.services.interf.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController implements PaymentDocs {

    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> processPayment(@AuthenticationPrincipal User user, @PathVariable Long orderId) throws StripeException {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.processPayment(user, orderId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> findPayment(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.findPaymentById(user, orderId));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> confirmPayment(@AuthenticationPrincipal User user, @PathVariable Long orderId) throws StripeException {
        return ResponseEntity.ok(paymentService.confirmPayment(user, orderId));
    }
}
