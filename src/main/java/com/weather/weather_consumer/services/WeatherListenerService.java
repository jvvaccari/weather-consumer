package com.weather.weather_consumer.services;

import com.weather.weather_consumer.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class WeatherListenerService {

    private String lastMessage = "";

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida do RabbitMQ: " + message);
        lastMessage = message;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}