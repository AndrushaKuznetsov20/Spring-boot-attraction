package com.trueman.attractions.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueman.attractions.client.WeatherClient;
import lombok.Data;
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
    public ResponseEntity<?> getCurrentWeather(double latitude, double longitude) throws JsonProcessingException {
        String location = latitude + "," + longitude;
        String result = weatherClient.getCurrentWeather(key, "no", location);

        JsonNode jsonNode = objectMapper.readTree(result);
        Double temp_c = jsonNode.path("current").path("temp_c").asDouble();
        String description = jsonNode.path("current").path("condition").path("text").asText();

        return ResponseEntity.ok("Погода: "+ temp_c + "C" + ";" + " " + "Описание: " + description + ";");
    }
}
