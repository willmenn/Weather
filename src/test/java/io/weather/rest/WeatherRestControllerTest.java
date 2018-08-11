package io.weather.rest;

import io.weather.domain.WeatherAverage;
import io.weather.service.WeatherAverageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WeatherRestControllerTest {

    @Mock
    private WeatherAverageService service;

    @InjectMocks
    private WeatherRestController controller;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldBeAbleToReturnTheWeatherAverage() {
        String anyCity = "anyCity";
        when(service.getWeatherAverageFor3days(eq(anyCity))).thenReturn(new WeatherAverage());

        WeatherAverage weather = controller.getWeather(anyCity);
        assertNotNull(weather);
    }
}