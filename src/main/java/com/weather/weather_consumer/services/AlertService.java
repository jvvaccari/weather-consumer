package com.weather.weather_consumer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.weather.weather_consumer.interfaces.WeatherDataListener;
import com.weather.weather_consumer.classes.WeatherInternalPublisher;
import java.util.Optional;
import com.weather.weather_consumer.services.HistoryService;

@Component
public class AlertService implements WeatherDataListener {

    private final WeatherInternalPublisher publisher;
    private final ObjectMapper objectMapper;
    private final HistoryService historyService;
    private JsonNode alerts;

    public AlertService(WeatherInternalPublisher publisher, ObjectMapper objectMapper,HistoryService historyService) {
        this.publisher = publisher;
        this.objectMapper = objectMapper;
        this.historyService = historyService;
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
            double apparentTemperature = firstElement.get("apparentTemperature").asDouble();

            System.out.println(temperature +" | "+ windSpeed +" | "+ apparentTemperature);
            ObjectNode alertNode = objectMapper.createObjectNode();

            boolean hasAlert = false;

            if (temperature < 0) {
                alertNode.put("Temperatura", "Temperatura abaixo dos 0 graus celsius detectada");
                hasAlert = true;
            }

            if (temperature > 20) {
                alertNode.put("Temperatura", "Temperatura acima dos 20 graus celsius detectada");
                hasAlert = true;
                System.out.println("Alerta de temperatura elevada");
            }

            if (windSpeed >= 2 ) {
                alertNode.put("Vento", "Ventos acima de 2 m/s");
                hasAlert = true;
                System.out.println("Alerta de ventos acima de 16m/s");
            }

            if (apparentTemperature < 0) {
                alertNode.put("Sensação Térmica", "Sensação térmica abaixo dos 0 graus Celsius");
                hasAlert = true;
                System.out.println("Alerta de sensação térmica levada");
            }

            alerts = hasAlert ? alertNode : null;

            historyService.updateAlertsIfChanged(alerts);

        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao processar JSON: " + e.getMessage());
        }
    }

    public Optional<JsonNode> getAlerts() {
        return Optional.ofNullable(alerts);
    }
}
