package com.weather.weather_consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "weather.exchange";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue myQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(Queue myQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(myQueue).to(fanoutExchange);
    }
}
