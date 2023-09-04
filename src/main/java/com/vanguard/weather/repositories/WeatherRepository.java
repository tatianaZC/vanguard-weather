package com.vanguard.weather.repositories;

import com.vanguard.weather.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, String> {}

