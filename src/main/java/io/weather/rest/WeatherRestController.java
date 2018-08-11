package io.weather.rest;

import io.weather.domain.WeatherAverage;
import io.weather.service.WeatherAverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
public class WeatherRestController {

    private WeatherAverageService service;

    @Autowired
    public WeatherRestController(WeatherAverageService service) {
        this.service = service;
    }

    @GetMapping(value = "/data")
    public WeatherAverage getWeather(@RequestParam @Valid @NotEmpty String city) {
        return service.getWeatherAverageFor3days(city);
    }
}
