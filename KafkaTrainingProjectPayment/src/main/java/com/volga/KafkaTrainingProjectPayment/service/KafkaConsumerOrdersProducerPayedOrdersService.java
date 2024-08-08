package com.volga.KafkaTrainingProjectPayment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class KafkaConsumerOrdersProducerPayedOrdersService {

    @Autowired
    private final KafkaTemplate<String, String> kafkaConsumerOrdersServiceTemplate;

    @Autowired
    private final KafkaTemplate<String, String> kafkaProducerPayedOrdersServiceTemplate;

    public KafkaConsumerOrdersProducerPayedOrdersService
            (KafkaTemplate<String, String> kafkaConsumerOrdersServiceTemplate,
             KafkaTemplate<String, String> kafkaProducerPayedOrdersService) {
        this.kafkaConsumerOrdersServiceTemplate = kafkaConsumerOrdersServiceTemplate;
        this.kafkaProducerPayedOrdersServiceTemplate = kafkaProducerPayedOrdersService;
    }

    @Transactional
    @KafkaListener(id = "ordersGroup", topics = "new_orders", groupId = "orders_consumer")
    public void newOrdersListener(String order) {
        log.info("Получен новый заказ {}", order);
        String payedOrder = "Оплаченный " +  order;
        log.info("Оплаченный заказ отправлен на сервис отгрузки в кафку");
        kafkaProducerPayedOrdersServiceTemplate.send("payed_orders", payedOrder);
    }
}
