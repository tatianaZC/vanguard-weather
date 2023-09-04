package com.vanguard.weather.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Sys {
    private Integer type;
    private Integer id;
    private String country;
    private Integer sunrise;
    private Integer sunset;
}