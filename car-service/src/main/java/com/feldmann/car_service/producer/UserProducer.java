package com.feldmann.car_service.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserProducer {
    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Map<String, Object> requestUserDetails(Long userId) {
        return (Map<String, Object>) rabbitTemplate.convertSendAndReceive("user.queue", userId);
    }

}
