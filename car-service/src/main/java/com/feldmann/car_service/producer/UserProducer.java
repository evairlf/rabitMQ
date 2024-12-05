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
        Object response = rabbitTemplate.convertSendAndReceive("user.queue", userId);
        System.out.println("Received response: " + response);
        return (Map<String, Object>) response;
    }

}
