package br.com.pedrohbhrj.DTO.response;

import br.com.pedrohbhrj.models.enums.OrderStatus;

import java.math.BigDecimal;

public record OrderResponse (Long id,
                             OrderStatus orderStatus,
                             BigDecimal total){
}
