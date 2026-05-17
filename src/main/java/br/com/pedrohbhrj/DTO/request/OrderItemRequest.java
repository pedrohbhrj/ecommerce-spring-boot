package br.com.pedrohbhrj.DTO.request;


import java.math.BigDecimal;

public record OrderItemRequest (BigDecimal unitPrice,
                                Integer quantity,
                                Long productId,
                                BigDecimal subTotal){
}
