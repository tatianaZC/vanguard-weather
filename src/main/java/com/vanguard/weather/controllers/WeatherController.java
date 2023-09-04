package com.vanguard.weather.controllers;

import com.vanguard.weather.clients.dto.Response;
import com.vanguard.weather.clients.params.Param;
import com.vanguard.weather.services.WeatherService;
import com.vanguard.weather.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService jsonPlaceHolderService) {
        weatherService = jsonPlaceHolderService;
    }

    @Operation(summary = "Find the current weather using an api key",
            description = "Get current weather",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
                    @ApiResponse(responseCode = "404", description = "Weather not found")
            })
    @PostMapping()
    public ResponseEntity<StandardResponse<String>> getCurrentWeather(@RequestParam("cityName") String cityName,
                                                                      @RequestParam("countryName") String countryName,
                                                                      @RequestParam("apiKey") String apiKey) throws Exception {

        Param param = new Param(cityName + "," + countryName, apiKey);
        Response response = weatherService.validateAndProcessAccessToWeather(param);
        return ResponseEntity.ok(new StandardResponse<>(response.getDescription()));
    }

    @Operation(summary = "Find the weather report",
            description = "Get weather from database",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
                    @ApiResponse(responseCode = "404", description = "Weather not found")
            })
    @GetMapping("/report")
    public ResponseEntity<StandardResponse<List<Response>>> getDataWeather() {
        List<Response> response = weatherService.getReportWeather();
        return ResponseEntity.ok(new StandardResponse<>(response));
    }
}
