package br.com.pedrohbhrj.services.interf;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;
import br.com.pedrohbhrj.models.User;

public interface PaymentService {

    PaymentResponse processPayment(User user,Long orderId);
    PaymentResponse findPaymentById(User user,Long orderId);
}
