package com.trueman.attractions.dto.weather;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseClient {
    public double temp_c;
    public String condition;
    public String recommendations;

    public ResponseClient(double temp_c, String condition, String recommendations) {
        this.temp_c = temp_c;
        this.condition = condition;
        this.recommendations = recommendations;
    }
}
