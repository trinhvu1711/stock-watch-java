package com.trinhvu.payment.kafka;

import com.trinhvu.payment.viewmodel.PaymentOrderStatusVm;
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
    private final KafkaTemplate<String, PaymentOrderStatusVm> kafkaTemplate;

    public void paymentConfirmation(PaymentOrderStatusVm paymentOrderStatusVm) {
        log.info("Order confirmation");
        Message<PaymentOrderStatusVm> message = MessageBuilder
                .withPayload(paymentOrderStatusVm)
                .setHeader(TOPIC, "payment-topic")
                .build();
        kafkaTemplate.send(message);
    }


}
