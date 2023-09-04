package com.vanguard.weather.clients;

import com.vanguard.weather.clients.params.Param;
import com.vanguard.weather.clients.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "weather", url = "api.openweathermap.org/data/2.5/weather")
public interface WeatherClient {

    @RequestMapping(method = RequestMethod.GET)
    Response getCurrentWeather(@SpringQueryMap Param param);
}