package br.com.pedrohbhrj.mapper;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;
import br.com.pedrohbhrj.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PaymentMapper {

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "clientSecret", ignore = true)
    PaymentResponse toResponse(Payment payment);

    @Mapping(target = "orderId", source = "order.id")
    PaymentResponse toResponse(Payment payment, String clientSecret);
}
