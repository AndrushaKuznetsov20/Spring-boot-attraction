package com.trueman.attractions.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueman.attractions.client.WeatherClient;
import com.trueman.attractions.dto.weather.ResponseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClient weatherClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${weather.api.key}")
    private String key;

    @Cacheable(value = "weather", key = "#latitude + ',' + #longitude")
    public ResponseEntity<ResponseClient> getCurrentWeather(double latitude, double longitude) throws JsonProcessingException {
        String location = latitude + "," + longitude;
        String result = weatherClient.getCurrentWeather(key, "no", location);

        JsonNode jsonNode = objectMapper.readTree(result);
        Double temp_c = jsonNode.path("current").path("temp_c").asDouble();
        String condition = jsonNode.path("current").path("condition").path("text").asText();

        boolean rain = condition.toLowerCase().contains("rain");
        boolean clear = condition.toLowerCase().contains("clear");
        boolean cloudy = condition.toLowerCase().contains("cloudy");

        String recommendations = generateRecommendations(temp_c);

        if (rain) {
            condition = "Дождливо !";
        }
        if (clear) {
            condition = "Облачно !";
        }
        if (cloudy) {
            condition = "Солнечно !";
        }

        ResponseClient response = new ResponseClient(temp_c, condition, recommendations);

        return ResponseEntity.ok(response);
    }

    private String generateRecommendations(double temp_c) {
        StringBuilder recommendations = new StringBuilder();

        recommendations.append("Рекомендации по температуре: ");

        if (temp_c < 0.0) {
            recommendations.append("На улице холодно, одевайтесь теплее !");
        } else {
            if (temp_c >= 0.0 && temp_c <= 15.0) {
                recommendations.append("На улице прохладно, наденьте лёгкую куртку !");
            } else {
                if (temp_c >= 16.0 && temp_c <= 25.0) {
                    recommendations.append("На улице тепло !");
                } else {
                    recommendations.append("На улице жара, наденьте шорты и шлёпки !");
                }
            }
        }

        return recommendations.toString().trim();
    }
}
