package br.com.pedrohbhrj.DTO.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequest (@Positive(message = "Must be positive") BigDecimal unitPrice,
                                @Min(value = 1,message = "Must be positive") Integer quantity,
                                @NotNull Long productId,
                                @Positive(message = "Must be positive") BigDecimal subTotal){
}
