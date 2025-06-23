package com.weather.weather_consumer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.weather.weather_consumer.interfaces.WeatherDataListener;
import com.weather.weather_consumer.classes.WeatherInternalPublisher;
import java.util.Optional;

@Component
public class AlertService implements WeatherDataListener {

    private final WeatherInternalPublisher publisher;
    private final ObjectMapper objectMapper;
    private JsonNode alert;

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
                System.err.println("[ERRO] JSON recebido não é um array válido ou está vazio.");
                return;
            }

            JsonNode firstElement = root.get(0);

            if (!firstElement.has("temperature2m") || !firstElement.has("windSpeed10m")) {
                System.err.println("[ERRO] Campos 'temperature2m' ou 'windSpeed10m' não encontrados.");
                return;
            }

            double temperature = firstElement.get("temperature2m").asDouble();
            double windSpeed = firstElement.get("windSpeed10m").asDouble();

            ObjectNode alertNode = objectMapper.createObjectNode();

            boolean hasAlert = false;

            if (temperature <= 0.9) {
                System.out.println("[ALERTA] Baixa temperatura detectada!");
                alertNode.put("Temperatura", "Baixa temperatura detectada!");
                hasAlert = true;
            }

            if (windSpeed >= 10) {
                System.out.println("[ALERTA] Ventos acima de 10m!");
                alertNode.put("Vento", "Ventos acima de 10m detectados!");
                hasAlert = true;
            }

            alert = hasAlert ? alertNode : null;

        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao processar JSON: " + e.getMessage());
        }
    }

    public Optional<JsonNode> getAlerts() {
        return Optional.ofNullable(alert);
    }
}
