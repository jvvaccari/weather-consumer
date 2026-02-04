package com.weather.weather_consumer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class ProducerTriggerService {

    private final RestClient restClient;

    public ProducerTriggerService(
            RestClient.Builder restClientBuilder,
            @Value("${weather.producer.base-url:http://localhost:8088}") String producerBaseUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(producerBaseUrl).build();
    }

    public boolean fetchNow() {
        try {
            restClient.post()
                    .uri("/weather/fetch-now")
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (RestClientException ex) {
            return false;
        }
    }
}
