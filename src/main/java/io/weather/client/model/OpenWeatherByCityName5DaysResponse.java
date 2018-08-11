package io.weather.client.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OpenWeatherByCityName5DaysResponse {
    List<Weather> list;

    @Value
    public static class Weather {
        private Main main;
        private String dt_txt;
    }

    @Value
    public static class Main {
        private Double temp;
        private Double pressure;
    }
}
