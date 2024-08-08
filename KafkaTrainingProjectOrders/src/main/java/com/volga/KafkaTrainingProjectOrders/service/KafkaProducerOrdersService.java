package com.volga.KafkaTrainingProjectOrders.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerOrdersService {

    private final KafkaTemplate<String, String> kafkaProducerTemplate;

    public KafkaProducerOrdersService(KafkaTemplate<String, String> kafkaProducerTemplate) {
        this.kafkaProducerTemplate = kafkaProducerTemplate;
    }

    public void sendMessageInTopic(String data) {
        log.info("Отправляется заказ в кафку");
        kafkaProducerTemplate.send("new_orders", data);
    }
}
