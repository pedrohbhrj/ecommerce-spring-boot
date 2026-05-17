package br.com.pedrohbhrj.services.interf;

import br.com.pedrohbhrj.DTO.request.OrderRequest;
import br.com.pedrohbhrj.DTO.response.OrderResponse;
import br.com.pedrohbhrj.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;


public interface OrderService {
    Map<String,String> createOrder(User user, OrderRequest request);

    OrderResponse findOrderById(Long orderId);

    Page<OrderResponse> findAllOrders(int page,int size,User user);
}
