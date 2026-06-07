package br.com.pedrohbhrj.services.interf;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;
import br.com.pedrohbhrj.models.User;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentResponse processPayment(User user,Long orderId) throws StripeException;
    PaymentResponse findPaymentById(User user,Long orderId);
    PaymentResponse confirmPayment(User user,Long orderId) throws StripeException;
}
