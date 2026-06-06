package br.com.pedrohbhrj.DTO.response;

import br.com.pedrohbhrj.models.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentResponse (Long id,
                               String transactionId,
                               Long orderId,
                               PaymentStatus paymentStatus,
                               BigDecimal amount,
                               String clientSecret){
}
