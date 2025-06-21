package com.weather.weather_consumer.classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.weather.weather_consumer.interfaces.WeatherDataListener;
import java.util.Optional;

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

    private Optional<JsonNode> createError(String message) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("error", message);
        return Optional.of(errorNode);
    }

    @Override
    public Optional<JsonNode> onDataReceived(String jsonData) {
        try {
            JsonNode root = objectMapper.readTree(jsonData);

            if (!root.isArray() || root.size() == 0) {
                return createError("JSON recebido não é um array válido ou está vazio.");
            }

            JsonNode firstElement = root.get(0);

            if (!firstElement.has("temperature2m") || !firstElement.has("windSpeed10m")) {
                return createError("Campos não encontrados.");
            }

            double temperature = firstElement.get("temperature2m").asDouble();
            double windSpeed = firstElement.get("windSpeed10m").asDouble();

            ObjectNode alertNode = objectMapper.createObjectNode();
            alertNode.set("data", firstElement);

            boolean hasAlert = false;

            if (temperature <= 0.9) {
                System.out.println("[ALERTA] Baixa temperatura detectada!");
                alertNode.put("tempAlert", "Baixa temperatura detectada!");
                hasAlert = true;
            }

            if (windSpeed >= 10) {
                System.out.println("[ALERTA] Ventos acima de 10m!");
                alertNode.put("windSpeedAlert", "Ventos acima de 10m detectados!");
                hasAlert = true;
            }

            return hasAlert ? Optional.of(alertNode) : Optional.empty();

        } catch (Exception e) {
            return createError(e.getMessage());
        }
    }
}
