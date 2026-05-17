package br.com.pedrohbhrj.DTO.request;


import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(@NotNull List<OrderItemRequest> orderItemList) {
}
