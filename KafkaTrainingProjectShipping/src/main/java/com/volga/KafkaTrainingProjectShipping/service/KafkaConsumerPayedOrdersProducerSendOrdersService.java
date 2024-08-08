package com.volga.KafkaTrainingProjectShipping.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class KafkaConsumerPayedOrdersProducerSendOrdersService {

    private final KafkaTemplate<String, String> kafkaConsumerPayedOrdersTemplate;
    private final KafkaTemplate<String, String> kafkaProducerSendOrdersTemplate;

    public KafkaConsumerPayedOrdersProducerSendOrdersService
            (KafkaTemplate<String, String> kafkaConsumerPayedOrdersTemplate,
             KafkaTemplate<String, String> kafkaProducerSendOrdersTemplate) {
        this.kafkaConsumerPayedOrdersTemplate = kafkaConsumerPayedOrdersTemplate;
        this.kafkaProducerSendOrdersTemplate = kafkaProducerSendOrdersTemplate;
    }

    @Transactional
    @KafkaListener(id = "ordersGroup", topics = "payed_orders", groupId = "orders_consumer")
    public void payedOrdersListener(String payedOrder) {
        log.info("Получен оплаченный заказ {}", payedOrder);
        String sendOrder = "Отправленный " + payedOrder;
        log.info("Информация об отправке заказа направлена в кафку");
        kafkaProducerSendOrdersTemplate.send("send_orders", sendOrder);
    }
}
