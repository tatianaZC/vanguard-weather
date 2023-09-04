package com.vanguard.weather.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Coord {
    private Double lon;
    private Double lat;
}