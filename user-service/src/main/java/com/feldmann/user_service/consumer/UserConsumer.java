package com.feldmann.user_service.consumer;

import com.feldmann.user_service.model.User;
import com.feldmann.user_service.model.dto.UserDTO;
import com.feldmann.user_service.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserConsumer {
    private final UserRepository userRepository;

    public UserConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "user.queue")
    public Map<String, Object> getUserDetails(Long userId) {
        long startTime = System.currentTimeMillis();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());

        long endTime = System.currentTimeMillis();
        System.out.println("Processing time for user " + userId + ": " + (endTime - startTime) + "ms");

        return response;
    }
}
