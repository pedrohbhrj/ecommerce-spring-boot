package br.com.pedrohbhrj.services.impl;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;
import br.com.pedrohbhrj.exceptions.NotFoundException;
import br.com.pedrohbhrj.exceptions.StockLimitExceededException;
import br.com.pedrohbhrj.infra.payment.StripeFakePayment;
import br.com.pedrohbhrj.mapper.PaymentMapper;
import br.com.pedrohbhrj.models.*;
import br.com.pedrohbhrj.models.enums.OrderStatus;
import br.com.pedrohbhrj.models.enums.PaymentStatus;
import br.com.pedrohbhrj.repository.OrderItemRepository;
import br.com.pedrohbhrj.repository.OrderRepository;
import br.com.pedrohbhrj.repository.PaymentRepository;
import br.com.pedrohbhrj.repository.ProductRepository;
import br.com.pedrohbhrj.services.interf.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    private final PaymentMapper paymentMapper;


    @Override
    @Transactional
    public PaymentResponse processPayment(User user, Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Access denied.");
        }

        Payment payment = paymentRepository.findByOrderId(order.getId()).orElseThrow(() -> new NotFoundException("Payment not found"));

        payment.setPaymentStatus(PaymentStatus.PROCESSING);

        List<OrderItem> list = orderItemRepository.findAllByOrderId(order.getId());


        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : list) {

            Optional<Product> productOpt = productRepository.findById(item.getProduct().getId());

            productOpt.ifPresent(product -> {

                if (item.getQuantity() > product.getStockQuantity() || product.getStockQuantity() == 0) {
                    payment.setPaymentStatus(PaymentStatus.DECLINED);
                    paymentRepository.save(payment);
                    throw new StockLimitExceededException("Stock Limit exceeded");
                }

                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());

                productRepository.save(product);
            });


            if (productOpt.isEmpty()) {
                payment.setPaymentStatus(PaymentStatus.DECLINED);
                paymentRepository.save(payment);
                throw new NotFoundException("Product not found");
            }


            total = total.add(item.getSubTotal());
        }


        order.setTotal(total);

        String transaction = StripeFakePayment.processPayment();

        payment.setTransactionId(transaction);

        payment.setAmount(order.getTotal());

        payment.setPaymentStatus(PaymentStatus.APPROVED);

        order.setOrderStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);

        Payment paymentSaved = paymentRepository.save(payment);

        log.info("Payment created successfully, id: {}", paymentSaved.getId());

        return paymentMapper.toResponse(paymentSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse findPaymentById(User user, Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new NotFoundException("Payment not found"));

        if (!payment.getOrder().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Access denied.");
        }

        log.info("Payment found successfully, id: {}", payment.getId());
        return paymentMapper.toResponse(payment);
    }
}
