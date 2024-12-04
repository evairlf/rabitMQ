package com.feldmann.car_service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {
    @RabbitListener(queues = "user.queue")
    public void listen(String message) {
        System.out.println("Mensagem recebida do user-service: " + message);
    }
}
