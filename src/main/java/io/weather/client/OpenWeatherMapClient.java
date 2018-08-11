package io.weather.client;

import io.weather.client.exception.NotFoundCityException;
import io.weather.client.model.OpenWeatherByCityName5DaysResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Component
public class OpenWeatherMapClient {

    private RestTemplate restTemplate;
    private static final String baseUrl = "http://api.openweathermap.org/data/2.5/forecast?" +
            "q=%s&mode=json&units=metric&appid=456c0d619db09215e050fa184bd1a90e";

    public OpenWeatherMapClient() {
        this.restTemplate = new RestTemplate();
    }

    public OpenWeatherByCityName5DaysResponse getWeatherFor5Days(String city) {
        String url = format(baseUrl, city);
        try {
            return restTemplate
                    .getForEntity(url, OpenWeatherByCityName5DaysResponse.class)
                    .getBody();
        } catch (HttpClientErrorException exception) {
            throw new NotFoundCityException();
        }
    }
}
