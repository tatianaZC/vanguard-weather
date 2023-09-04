package com.vanguard.weather.services;

import com.vanguard.weather.clients.WeatherClient;
import com.vanguard.weather.clients.dto.*;
import com.vanguard.weather.clients.params.Param;
import com.vanguard.weather.exceptions.LimitRequestException;
import com.vanguard.weather.exceptions.MissingApiKeyException;
import com.vanguard.weather.models.Subscription;
import com.vanguard.weather.models.Weather;
import com.vanguard.weather.repositories.SubscriptionRepository;
import com.vanguard.weather.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    @Value("${weather.hour}")
    private Integer HOURS;

    @Value("${weather.requests}")
    private Integer REQUESTS;

    private final SubscriptionRepository subscriptionRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherClient weatherClient;
    private final SubscriptionService subscriptionService;

    public WeatherService(WeatherClient jsonPlaceHolderClient, WeatherRepository weatherRepository, SubscriptionRepository subscriptionRepository, SubscriptionService subscriptionService) {
        this.weatherClient = jsonPlaceHolderClient;
        this.weatherRepository = weatherRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;
    }

    @Transactional
    public Response validateAndProcessAccessToWeather(Param param) throws Exception {
        if (Objects.isNull(param.getAppid())) {
            throw new MissingApiKeyException("You need to provide an API KEY");
        }

        Optional<Subscription> subscription = subscriptionRepository.findById(param.getAppid());

        if (subscription.isPresent()) {
            validateAccessLimit(subscription.get());
        } else {
            createSubscription(param.getAppid());
        }

        Response currentWeather = weatherClient.getCurrentWeather(param);
        saveDataWeather(currentWeather, param.getAppid());
        return currentWeather;
    }

    public void createSubscription(String apiKey) {
        final Integer DEFAULT_HOURS = 1;
        final Integer DEFAULT_REQUESTS = 5;

        Subscription subscriptionToSave = new Subscription();
        subscriptionToSave.setApiKey(apiKey);
        subscriptionToSave.setStartDate(LocalDateTime.now());
        subscriptionToSave.setEndDate(LocalDateTime.now().plusHours(Optional.ofNullable(HOURS).orElse(DEFAULT_HOURS)));
        subscriptionToSave.setRemainingRequest(Optional.ofNullable(REQUESTS).orElse(DEFAULT_REQUESTS) - 1);
        subscriptionRepository.save(subscriptionToSave);
    }

    public void updateSubscription(Subscription subscription) {
        subscription.setRemainingRequest(subscription.getRemainingRequest() - 1);
        subscriptionRepository.save(subscription);
    }

    public List<Response> getReportWeather() {
        return weatherRepository.findAll().stream()
                .map(this::mapWeatherResponse)
                .collect(Collectors.toList());
    }

    public ArrayList<CurrentWeather> setWeather(Integer weatherId, String getWeatherMain, String getWeatherDescription, String getWeatherIcon) {

        CurrentWeather weather = CurrentWeather.builder()
                .id(weatherId)
                .main(getWeatherMain)
                .description(getWeatherDescription)
                .icon(getWeatherIcon)
                .build();

        return new ArrayList<>(List.of(weather));
    }

    public void validateAccessLimit(Subscription subscription) throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(subscription.getStartDate()) && currentTime.isBefore(subscription.getEndDate())) {
            validateNumberAccesses(subscription);
        } else {
            createSubscription(subscription.getApiKey());
        }
    }

    public void validateNumberAccesses(Subscription subscription) {
        if (subscription.getRemainingRequest() > BigDecimal.ZERO.intValue()) {
            updateSubscription(subscription);
        } else {
            throw new LimitRequestException("You have exceeded the limit of requests by one hour, please try again later");
        }
    }

    public void saveDataWeather(Response currentWeather, String appId) {
        Weather weather = subscriptionService.buildDataWeather(currentWeather, appId);
        weatherRepository.save(weather);
    }

    private Response mapWeatherResponse(Weather weather) {
        Coord coord = subscriptionService.buildDataCoord(weather);
        Main main = subscriptionService.buildDataMain(weather);
        Wind wind = subscriptionService.buildDataWind(weather);
        Sys sys = subscriptionService.buildDataSys(weather);

        return Response.builder()
                .base(weather.getBase())
                .visibility(weather.getVisibility())
                .dt(weather.getDt())
                .timezone(weather.getTimezone())
                .id(weather.getId())
                .name(weather.getName())
                .cod(weather.getCod())
                .clouds(new Clouds(weather.getCloudsAll()))
                .coord(coord)
                .main(main)
                .wind(wind)
                .sys(sys)
                .weather(setWeather(weather.getWeatherId(), weather.getWeatherMain(), weather.getWeatherDescription(), weather.getWeatherIcon()))
                .build();
    }
}