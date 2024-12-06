package com.neoleaptask.NeoleapTask.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Define Queue
    @Bean
    public Queue paymentQueue() {
        return new Queue("paymentQueue", true);
    }

    // Define Exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("paymentExchange");
    }

    // Define Binding between Queue and Exchange
    @Bean
    public Binding binding(Queue paymentQueue, TopicExchange exchange) {
        return new Binding("paymentQueue", Binding.DestinationType.QUEUE, "paymentExchange", "paymentRoutingKey", null);
    }
}
