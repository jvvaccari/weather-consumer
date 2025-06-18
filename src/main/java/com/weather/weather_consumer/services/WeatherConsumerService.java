package com.weather.weather_consumer.services;

import com.weather.weather_consumer.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class WeatherConsumerService {

    private String lastData = null;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveFromProducer(String data) {
        System.out.println("Dado recebido do Producer: " + data);
        lastData = data;
    }

    public String getLastData() {
        return lastData;
    }
}