package com.weather.weather_consumer.services;

import com.weather.weather_consumer.RabbitMQConfig;
import com.weather.weather_consumer.classes.WeatherInternalPublisher;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class WeatherConsumerService {

    private final WeatherInternalPublisher internalPublisher;

    public WeatherConsumerService(WeatherInternalPublisher internalPublisher) {
        this.internalPublisher = internalPublisher;
    }

    @RabbitListener(queues = "#{myQueue.name}")
    public void receiveFromProducer(String data) {
        internalPublisher.notifyListeners(data);
    }
}
