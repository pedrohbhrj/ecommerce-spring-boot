package br.com.pedrohbhrj.DTO.request;

import br.com.pedrohbhrj.models.OrderItem;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(@NotNull List<OrderItem> orderItemList) {
}
