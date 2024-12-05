package com.feldmann.car_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyTimeout(5000);
        rabbitTemplate.setMessageConverter(jacksonConverter());
        rabbitTemplate.setChannelTransacted(false);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Fila durável para enviar para o user-service
    @Bean
    public Queue userQueue() {
        return new Queue("user.queue", true, false, false);  // Durável
    }

    // Fila durável para consumir no car-service, caso necessário
    @Bean
    public Queue carQueue() {
        return new Queue("car.queue", true, false, false);  // Durável
    }

    // Definindo a troca (TopicExchange)
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("user-exchange");
    }

    // Configurando o binding entre fila e troca
    @Bean
    public Binding binding(Queue userQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with("user.routing.key");
    }
}

