package br.com.pedrohbhrj.services.impl;

import br.com.pedrohbhrj.DTO.request.OrderItemRequest;
import br.com.pedrohbhrj.DTO.request.OrderRequest;
import br.com.pedrohbhrj.DTO.response.OrderResponse;
import br.com.pedrohbhrj.exceptions.NotFoundException;
import br.com.pedrohbhrj.exceptions.StockLimitExceededException;
import br.com.pedrohbhrj.models.*;
import br.com.pedrohbhrj.models.enums.OrderStatus;
import br.com.pedrohbhrj.models.enums.PaymentStatus;
import br.com.pedrohbhrj.repository.*;
import br.com.pedrohbhrj.services.interf.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Map<String,String> createOrder(User user, OrderRequest request) {

        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUser(user);

        List<OrderItem> listValidated = request.orderItemList()
                .stream()
                .map(item -> {

                    Product product = productRepository.findById(item.productId()).orElseThrow(() -> new NotFoundException("Product not found"));

                    if (item.quantity() > product.getStockQuantity()) {
                        throw new StockLimitExceededException("Stock Limit exceeded");
                    }

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(item.quantity());
                    orderItem.setUnit_price(product.getPrice());
                    orderItem.setSubTotal(product.getPrice().multiply(BigDecimal.valueOf(item.quantity())));

                    return orderItem;
                }).toList();


        Order orderSaved = orderRepository.save(order);

        listValidated.forEach(item -> item.setOrder(orderSaved));

        orderItemRepository.saveAll(listValidated);

        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.PENDING);

        payment.setOrder(orderSaved);

        paymentRepository.save(payment);

        log.info("Order created successfully, with status: {}",orderSaved.getOrderStatus().name());

        Map<String,String> orderResponse = new HashMap<>();

        orderResponse.put("orderId",String.valueOf(orderSaved.getId()));
        orderResponse.put("orderStatus",orderSaved.getOrderStatus().name());

        return orderResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findOrderById(User user,Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));

        if(!order.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("Access denied");
        }
        log.info("Order found , with status: {}",order.getOrderStatus().name());

        return new OrderResponse(orderId,order.getOrderStatus(),order.getTotal());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> findAllOrders(int page, int size, User user) {

        PageRequest request = PageRequest.of(page,size);

        Page<OrderResponse> response = orderRepository.findAllByUserId(user.getId(),request)
                .map(o -> new OrderResponse(o.getId(),o.getOrderStatus(),o.getTotal()));


        log.info("Orders found");


        return response;
    }
}
