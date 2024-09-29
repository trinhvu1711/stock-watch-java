package com.trinhvu.notification.kafka;

import com.trinhvu.notification.model.Notification;
import com.trinhvu.notification.repositories.NotificationRepository;
import com.trinhvu.notification.service.EmailService;
import com.trinhvu.notification.viewmodel.OrderConfirmation;
import com.trinhvu.notification.viewmodel.PaymentConfirmation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.trinhvu.notification.model.NotificationType.ORDER_CONFIRMATION;
import static com.trinhvu.notification.model.NotificationType.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumerPaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Payment confirmation: {}", paymentConfirmation);
        notificationRepository.save(Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .build()
        );

//        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(paymentConfirmation);
    }

    @KafkaListener(topics = "order-topic")
    public void consumerOrderConfirmNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Order confirmation: {}", orderConfirmation);
        notificationRepository.save(Notification.builder()
                .type(ORDER_CONFIRMATION)
                .orderConfirmation(orderConfirmation)
                .notificationDate(LocalDateTime.now())
                .build()
        );

//        var customerName = orderConfirmation.customer().firstname() + " " + orderConfirmation.customer().lastname();
        emailService.sendOrderConfirmationEmail(orderConfirmation);
    }
}
