package com.trinhvu.payment.kafka;

import com.trinhvu.payment.viewmodel.PaymentConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {
    private final KafkaTemplate<String, PaymentConfirmation> kafkaTemplate;

    public void paymentConfirmation(PaymentConfirmation paymentOrderStatusVm) {
        log.info("Order confirmation");
        Message<PaymentConfirmation> message = MessageBuilder
                .withPayload(paymentOrderStatusVm)
                .setHeader(TOPIC, "payment-topic")
                .build();
        kafkaTemplate.send(message);
    }


}
