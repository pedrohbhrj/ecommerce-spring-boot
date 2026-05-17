package br.com.pedrohbhrj.mapper;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;
import br.com.pedrohbhrj.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "orderId",source = "order.id")
    PaymentResponse toResponse(Payment payment);
}
