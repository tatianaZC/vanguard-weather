package com.vanguard.weather.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CurrentWeather {
    private Integer id;
    private String main;
    private String description;
    private String icon;
}