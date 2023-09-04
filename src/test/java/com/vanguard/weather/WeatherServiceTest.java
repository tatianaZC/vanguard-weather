package com.vanguard.weather;

import com.vanguard.weather.clients.WeatherClient;
import com.vanguard.weather.clients.dto.*;
import com.vanguard.weather.clients.params.Param;
import com.vanguard.weather.models.Subscription;
import com.vanguard.weather.models.Weather;
import com.vanguard.weather.repositories.SubscriptionRepository;
import com.vanguard.weather.repositories.WeatherRepository;
import com.vanguard.weather.services.SubscriptionService;
import com.vanguard.weather.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private SubscriptionService subscriptionService;

    @Test
    public void testValidateAndProcessAccessToWeather() throws Exception {
        // Arrange
        Param param = new Param("London,uk", "309r23948so34209834");
        param.setAppid("testApiKey");
        Weather weather = new Weather();

        // Mock the behavior of subscriptionRepository
        when(subscriptionRepository.findById("testApiKey")).thenReturn(Optional.empty());

        // Mock the behavior of weatherRepository
        when(weatherRepository.save(any())).thenReturn(weather);

        Response mockedWeatherResponse = buildResponseData();

        // Mock the behavior of weatherClient
        when(weatherClient.getCurrentWeather(param)).thenReturn(mockedWeatherResponse);

        // Act
        Response result = weatherService.validateAndProcessAccessToWeather(param);

        // Assert
        // Verify that the response is not null
        assertNotNull(result);

        // Verify that repository.save() was called with the expected Subscription object
        ArgumentCaptor<Subscription> subscriptionCaptor = ArgumentCaptor.forClass(Subscription.class);
        verify(subscriptionRepository).save(subscriptionCaptor.capture());
        Subscription savedSubscription = subscriptionCaptor.getValue();

        // Assert the properties of the savedSubscription
        assertEquals("testApiKey", savedSubscription.getApiKey());
        assertNotNull(savedSubscription.getStartDate());
        assertNotNull(savedSubscription.getEndDate());
        assertEquals(4, savedSubscription.getRemainingRequest());
    }

    public Response buildResponseData() {
        Coord coord = Coord.builder()
                .lon(12.55)
                .lat(34.66)
                .build();

        Main main = Main.builder()
                .temp(34.44)
                .feelsLike(289.4)
                .tempMin(289.25)
                .tempMax(291.08)
                .pressure(1024)
                .humidity(95)
                .build();

        Wind wind = Wind.builder()
                .speed(2.06)
                .deg(100)
                .build();

        Sys sys = Sys.builder()
                .type(2)
                .id(2075535)
                .country("GB")
                .sunrise(1693804642)
                .sunset(1693852946)
                .build();

        CurrentWeather weather = CurrentWeather.builder()
                .id(741)
                .main("Fog")
                .description("fog")
                .icon("50d")
                .build();

        return Response.builder()
                .base("stations")
                .visibility(300)
                .dt(1693807146)
                .timezone(3600)
                .id(2643743)
                .name("London")
                .cod(200)
                .clouds(new Clouds(100))
                .coord(coord)
                .main(main)
                .wind(wind)
                .sys(sys)
                .weather(new ArrayList<>(List.of(weather)))
                .build();
    }
}