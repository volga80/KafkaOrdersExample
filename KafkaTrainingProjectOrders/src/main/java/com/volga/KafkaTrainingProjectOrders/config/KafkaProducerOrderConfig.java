package com.volga.KafkaTrainingProjectOrders.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaProducerOrderConfig {

    @Bean
    public NewTopic newTopic(){
        return new NewTopic("new_orders", 3,(short) 1);
    }

    @Bean
    public NewTopic dlt() {
        return new NewTopic("new_orders-dlt", 3, (short) 1);
    }

    @Bean
    public StringMessageConverter converter() {
        return new StringMessageConverter();
    }

    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> operations) {
        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(operations),
                new FixedBackOff(2000L, 5));
    }
}
