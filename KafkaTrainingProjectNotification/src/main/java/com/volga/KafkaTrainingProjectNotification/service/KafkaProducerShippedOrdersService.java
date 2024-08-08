package com.volga.KafkaTrainingProjectNotification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class KafkaProducerShippedOrdersService {

    private final KafkaTemplate<String, String> kafkaConsumerShippedOrdersTemplate;

    public KafkaProducerShippedOrdersService(KafkaTemplate<String, String> kafkaConsumerShippedOrdersTemplate) {
        this.kafkaConsumerShippedOrdersTemplate = kafkaConsumerShippedOrdersTemplate;
    }

    @Transactional
    @KafkaListener(id = "ordersGroup", topics = "send_orders", groupId = "orders_consumer")
    public void sendOrdersListener(String sendOrder) {
        log.info("{} заказ доставлен получателю", sendOrder);
    }
}
