package br.com.pedrohbhrj.services.interf;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse processPayment(Long orderId);
    PaymentResponse findPaymentById(Long orderId);
}
