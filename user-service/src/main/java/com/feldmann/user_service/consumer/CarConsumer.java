package com.feldmann.user_service.consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.feldmann.user_service.consumer.carOperation.CarOperationMessage;
import com.feldmann.user_service.model.User;
import com.feldmann.user_service.repository.UserRepository;

@Component
public class CarConsumer {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CarConsumer.class);

    private final UserRepository userRepository;

    public CarConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @RabbitListener(queues = "user.queue")
    public void handleCarMessage(CarOperationMessage message) {
        logger.info("Received message: {}", message);
        try {
        if ("save".equals(message.getOperation())) {
            Long userId = message.getUserId();
            System.out.println(message.getUserId());
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));
            user.getCarIds().add(message.getCarId());
            userRepository.save(user);
            System.out.println("Carro adicionado ao usuário: " + userId);

        } else if ("delete".equals(message.getOperation())) {
            // Remover carro do usuário
            Long userId = message.getUserId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));
            
            user.getCarIds().remove(message.getCarId());
            userRepository.save(user);
            System.out.println("Carro removido do usuário: " + userId);
        } else {
            System.err.println("Operação desconhecida: " + message.getOperation());
        }
    } catch (Exception e) {
        e.printStackTrace(); // Verifique se algum erro ocorre
    }
    }
}
