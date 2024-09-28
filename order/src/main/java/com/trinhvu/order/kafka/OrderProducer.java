package com.trinhvu.order.kafka;

import com.trinhvu.order.viewmodel.order.OrderGetVm;
import com.trinhvu.order.viewmodel.order.OrderVm;
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
public class OrderProducer {
    private final KafkaTemplate<String, OrderVm> kafkaTemplate;

    public void orderConfirmation(OrderVm orderVm) {
        log.info("Order confirmation");
        Message<OrderVm> message = MessageBuilder
                .withPayload(orderVm)
                .setHeader(TOPIC, "order-topic")
                .build();
        kafkaTemplate.send(message);
    }


}
