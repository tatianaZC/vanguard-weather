package com.vanguard.weather.services;

import com.vanguard.weather.clients.dto.*;
import com.vanguard.weather.models.Weather;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SubscriptionService {

    public Weather buildDataWeather(Response currentWeather, String appId) {
        Weather weather = new Weather();
        weather.setApiKey(appId);
        weather.setExecutionDate(LocalDateTime.now());
        weather.setLongitude(currentWeather.getCoord().getLon());
        weather.setLatitude(currentWeather.getCoord().getLat());
        weather.setWeatherId(currentWeather.getWeather().get(0).getId());
        weather.setWeatherMain(currentWeather.getWeather().get(0).getMain());
        weather.setWeatherDescription(currentWeather.getWeather().get(0).getDescription());
        weather.setWeatherIcon(currentWeather.getWeather().get(0).getIcon());
        weather.setBase(currentWeather.getBase());
        weather.setMainTemp(currentWeather.getMain().getTemp());
        weather.setMainFeelsLike(currentWeather.getMain().getFeelsLike());
        weather.setMainTempMin(currentWeather.getMain().getTempMin());
        weather.setMainTempMax(currentWeather.getMain().getTempMax());
        weather.setMainPressure(currentWeather.getMain().getPressure());
        weather.setMainHumidity(currentWeather.getMain().getHumidity());
        weather.setVisibility(currentWeather.getVisibility());
        weather.setWindSpeed(currentWeather.getWind().getSpeed());
        weather.setWindDeg(currentWeather.getWind().getDeg());
        weather.setCloudsAll(currentWeather.getClouds().getAll());
        weather.setDt(currentWeather.getDt());
        weather.setSysType(currentWeather.getSys().getType());
        weather.setSysId(currentWeather.getSys().getId());
        weather.setSysCountry(currentWeather.getSys().getCountry());
        weather.setSysSunrise(currentWeather.getSys().getSunrise());
        weather.setSysSunset(currentWeather.getSys().getSunset());
        weather.setTimezone(currentWeather.getTimezone());
        weather.setId(currentWeather.getId());
        weather.setName(currentWeather.getName());
        weather.setCod(currentWeather.getCod());
        return weather;
    }

    public Coord buildDataCoord(Weather weather) {
        return Coord.builder()
                .lon(weather.getLongitude())
                .lat(weather.getLatitude())
                .build();
    }

    public Main buildDataMain(Weather weather) {
        return Main.builder()
                .temp(weather.getMainTemp())
                .feelsLike(weather.getMainFeelsLike())
                .tempMin(weather.getMainTempMin())
                .tempMax(weather.getMainTempMax())
                .pressure(weather.getMainPressure())
                .humidity(weather.getMainHumidity())
                .build();
    }

    public Wind buildDataWind(Weather weather) {
        return Wind.builder()
                .speed(weather.getWindSpeed())
                .deg(weather.getWindDeg())
                .build();
    }

    public Sys buildDataSys(Weather weather) {
        return Sys.builder()
                .type(weather.getSysType())
                .id(weather.getSysId())
                .country(weather.getSysCountry())
                .sunrise(weather.getSysSunrise())
                .sunset(weather.getSysSunset())
                .build();
    }
}
