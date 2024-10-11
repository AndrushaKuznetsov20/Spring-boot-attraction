package com.trueman.attractions.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather-client", url = "http://api.weatherapi.com/v1")
public interface WeatherClient {
    @GetMapping("/current.json")
    String getCurrentWeather(@RequestParam("key") String key,
                             @RequestParam("aqi") String aqi,
                             @RequestParam("q") String location);
}
