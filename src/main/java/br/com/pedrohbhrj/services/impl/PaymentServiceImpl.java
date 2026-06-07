package br.com.pedrohbhrj.services.impl;

import br.com.pedrohbhrj.DTO.response.PaymentResponse;
import br.com.pedrohbhrj.exceptions.NotFoundException;
import br.com.pedrohbhrj.exceptions.StockLimitExceededException;
import br.com.pedrohbhrj.infra.payment.StripePaymentService;
import br.com.pedrohbhrj.mapper.PaymentMapper;
import br.com.pedrohbhrj.models.*;
import br.com.pedrohbhrj.models.enums.OrderStatus;
import br.com.pedrohbhrj.models.enums.PaymentStatus;
import br.com.pedrohbhrj.repository.OrderItemRepository;
import br.com.pedrohbhrj.repository.OrderRepository;
import br.com.pedrohbhrj.repository.PaymentRepository;
import br.com.pedrohbhrj.repository.ProductRepository;
import br.com.pedrohbhrj.services.interf.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    private final PaymentMapper paymentMapper;

    private final StripePaymentService stripePaymentService;

    @Override
    @Transactional
    public PaymentResponse processPayment(User user, Long orderId) throws StripeException {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Access denied.");
        }

        Payment payment = paymentRepository.findByOrderId(order.getId()).orElseThrow(() -> new NotFoundException("Payment not found"));


        List<OrderItem> list = orderItemRepository.findAllByOrderId(order.getId());

        BigDecimal total = BigDecimal.ZERO;

        boolean paymentDeclined = false;

        for (OrderItem item : list) {

            Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(() -> new NotFoundException("Product not found."));

            if (item.getQuantity() > product.getStockQuantity()) {
                paymentDeclined = true;
                break;
            }

            total = total.add(item.getSubTotal());

        }

        if (paymentDeclined) {

            payment.setPaymentStatus(PaymentStatus.DECLINED);
            Payment paymentSaved = paymentRepository.save(payment);

            return paymentMapper.toResponse(paymentSaved);
        }


        order.setTotal(total);

        Long totalInCents = order.convertingIntoCents();

        HashMap<String, String> values = stripePaymentService.processPayment(totalInCents, user.getEmail());

        payment.setAmount(order.getTotal());

        String clientSecret = values.get("clientSecret");
        String transaction = values.get("transactionId");

        payment.setTransactionId(transaction);

        payment.setPaymentStatus(PaymentStatus.PROCESSING);

        orderRepository.save(order);

        Payment paymentSaved = paymentRepository.save(payment);

        log.info("Payment intent created successfully, id: {}", paymentSaved.getId());

        return paymentMapper.toResponse(paymentSaved, clientSecret);
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

    @Transactional
    public PaymentResponse confirmPayment(User user, Long orderId) throws StripeException {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found."));

        if (!order.getUser().getId().equals(user.getId())) {

            log.warn("User unathorized tried to confirm other payment user: {} ,in order : {}", user.getId(), orderId);

            throw new AccessDeniedException("Access denied.");
        }

        Payment payment = paymentRepository
                .findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        if (payment.getPaymentStatus().equals(PaymentStatus.APPROVED)) {
            return paymentMapper.toResponse(payment);
        }

        boolean isPaid = stripePaymentService.verifyStatusPayment(payment.getTransactionId());

        if (!isPaid) {
            log.warn("False attempt in confirm to the order {}.Transaction in stripe is not succeeded", orderId);
            throw new IllegalStateException("The payment isn't is approved.");
        }

        List<OrderItem> items = orderItemRepository
                .findAllByOrderId(orderId);

        for (OrderItem item : items) {

            Product product = productRepository
                    .findByIdWithPessimisticLock(item.getProduct().getId())
                    .orElseThrow(() -> new NotFoundException("Product not found."));
            if (item.getQuantity() > product.getStockQuantity()) {
                payment.setPaymentStatus(PaymentStatus.DECLINED);
                order.setOrderStatus(OrderStatus.CANCELED);
                paymentRepository.save(payment);
                orderRepository.save(order);
                throw new StockLimitExceededException("Order canceled , dont have enough stock.");
            }

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);

        }

        payment.setPaymentStatus(PaymentStatus.APPROVED);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        Payment paymentSaved = paymentRepository.save(payment);

        return paymentMapper.toResponse(paymentSaved);
    }
}
