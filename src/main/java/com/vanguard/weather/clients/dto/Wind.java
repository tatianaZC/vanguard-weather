package com.vanguard.weather.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Wind {
    private Double speed;
    private Integer deg;
}
