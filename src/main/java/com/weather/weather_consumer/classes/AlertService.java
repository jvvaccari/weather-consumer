package com.weather.weather_consumer.classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.weather.weather_consumer.interfaces.WeatherDataListener;

@Component
public class AlertService implements WeatherDataListener {

    private final WeatherInternalPublisher publisher;
    private final ObjectMapper objectMapper;

    public AlertService(WeatherInternalPublisher publisher, ObjectMapper objectMapper) {
        this.publisher = publisher;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        publisher.registerListener(this);
    }

    @Override
    public void onDataReceived(String jsonData) {
        try {
            JsonNode root = objectMapper.readTree(jsonData);

            if (!root.isArray() || root.size() == 0) {
                System.out.println("[ALERTA] JSON recebido não é um array válido ou está vazio.");
                return;
            }

            JsonNode firstElement = root.get(0);
            JsonNode tempNode = firstElement.get("temperature2m");

            if (tempNode == null) {
                System.out.println("[ALERTA] Campo 'temperature2m' não encontrado no primeiro objeto do array.");
                return;
            }

            double temperature = tempNode.asDouble();

            if (temperature <= 0.0) {
                System.out.println("[ALERTA] Baixa temperatura detectada!");
            } else {
                System.out.println("[ALERTA] Temperatura recebida: " + temperature);
            }
        } catch (Exception e) {
            System.out.println("[ALERTA] Erro ao ler JSON: " + e.getMessage());
        }
    }
}
