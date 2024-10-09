package com.trinhvu.order.service;

import com.trinhvu.order.exception.NotFoundException;
import com.trinhvu.order.kafka.OrderProducer;
import com.trinhvu.order.model.Order;
import com.trinhvu.order.model.OrderItem;
import com.trinhvu.order.viewmodel.customer.CustomerVm;
import com.trinhvu.order.viewmodel.order.PaymentOrderStatusVm;
import com.trinhvu.order.model.enumeration.OrderStatus;
import com.trinhvu.order.model.enumeration.PaymentStatus;
import com.trinhvu.order.repository.OrderRepository;
import com.trinhvu.order.viewmodel.order.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.trinhvu.order.utils.Constants.ErrorCode.ORDER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final StockService stockService;
    private final CartService cartService;
    private final CustomerService customerService;

    public OrderVm createOrder(@Valid OrderPostVm orderPostVm) {
        Order order = Order.builder()
                .orderStatus(orderPostVm.orderStatus())
                .email(orderPostVm.email())
                .note(orderPostVm.note())
                .numberItem(orderPostVm.numberItem())
                .orderStatus(orderPostVm.orderStatus())
                .paymentStatus(orderPostVm.paymentStatus())
                .paymentId(orderPostVm.paymentId())
                .checkoutId(orderPostVm.checkoutId())
                .totalPrice(orderPostVm.totalPrice())
                .build();

        orderRepository.save(order);

        Set<OrderItem> orderItems = orderPostVm.orderItem().stream()
                .map(item -> OrderItem.builder()
                        .orderId(order)
                        .stockId(item.stockId())
                        .stockName(item.stockName())
                        .quantity(item.quantity())
                        .stockPrice(item.stockPrice())
                        .note(item.note())
                        .build()
                ).collect(Collectors.toSet());

        order.setOrderItems(orderItems);
        OrderVm orderVm = OrderVm.fromModel(order);

        // subtract quantity
        stockService.subtractStockQuantity(orderVm);

        // remove from cart
        cartService.deleteCartItem(orderVm);

        CustomerVm customerVm = customerService.getCustomerProfile();

        // send notification
        OrderConfirmation orderConfirmation = OrderConfirmation.fromModel(order, customerVm);
        orderProducer.orderConfirmation(orderConfirmation);
        acceptOrder(orderVm.id());
        return orderVm;
    }

    public OrderGetVm getMyOrders() {
        return null;
    }

    public OrderListVm getAllOrders(ZonedDateTime createdFrom, ZonedDateTime createdTo, int pageNo, int pageSize, String email, String stockName) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage = orderRepository.findOrderByWithMulCriteria(
                email,
                stockName,
                createdFrom,
                createdTo,
                pageable
        );
        if (orderPage.isEmpty()){
            return new OrderListVm(null, 0, 0);
        }

        List<OrderBriefVm> orders = orderPage.getContent().stream()
                .map(OrderBriefVm::fromModel).toList();

        return new OrderListVm(orders, orderPage.getTotalElements(), orderPage.getTotalPages());
    }

    private void acceptOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new NotFoundException(ORDER_NOT_FOUND, orderId));
        order.setOrderStatus(OrderStatus.ACCEPTED);
        this.orderRepository.save(order);
    }

    public PaymentOrderStatusVm updateOrderPaymentStatus(@Valid PaymentOrderStatusVm paymentOrderStatusVm) {
        var order = this.orderRepository
                .findById(paymentOrderStatusVm.orderId())
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND, paymentOrderStatusVm.orderId()));

        order.setPaymentId(paymentOrderStatusVm.paymentId());
        String paymentStatus = paymentOrderStatusVm.paymentStatus();
        order.setOrderStatus(OrderStatus.valueOf(paymentStatus));
        if (PaymentStatus.COMPLETED.name().equals(paymentStatus)) {
            order.setOrderStatus(OrderStatus.COMPLETED);
        }
        Order result = this.orderRepository.save(order);
        return PaymentOrderStatusVm.builder()
                .orderId(result.getId())
                .orderStatus(result.getOrderStatus().getName())
                .paymentStatus(paymentOrderStatusVm.paymentStatus())
                .paymentId(paymentOrderStatusVm.paymentId())
                .email(result.getEmail())
                .build();
    }

    public Order findOrderByCheckoutId(String checkoutId) {
        return this.orderRepository.findByCheckoutId(checkoutId)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND,"of checkout id " + checkoutId));
    }
}
