package com.volga.KafkaTrainingProjectOrders.controller;

import com.volga.KafkaTrainingProjectOrders.service.KafkaProducerOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class OrderController {

    private final KafkaProducerOrdersService kafkaProducerOrdersService;

    public OrderController(KafkaProducerOrdersService kafkaProducerOrdersService) {
        this.kafkaProducerOrdersService = kafkaProducerOrdersService;
    }

    @PostMapping("/createNewOrder")
    @Transactional
    public ResponseEntity<String> sendOrderToKafka(@RequestBody String order)  {
      log.info("Создан заказ {}", order);
      kafkaProducerOrdersService.sendMessageInTopic(order);
      return ResponseEntity.ok(order);
    }
}
