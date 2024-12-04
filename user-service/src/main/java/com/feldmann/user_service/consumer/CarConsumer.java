package com.feldmann.user_service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CarConsumer {
    @RabbitListener(queues = "car.queue")
    public void listen(String message) {
        System.out.println("Mensagem recebida do car-service: " + message);
    }
}
