package io.weather.service;

import io.weather.client.OpenWeatherMapClient;

import io.weather.client.model.OpenWeatherByCityName5DaysResponse;
import io.weather.domain.WeatherAverage;
import io.weather.domain.WeatherAverage.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class WeatherAverageService {

    private OpenWeatherMapClient client;
    private DayAggregator dayAggregator;

    @Autowired
    public WeatherAverageService(OpenWeatherMapClient client, DayAggregator dayAggregator) {
        this.client = client;
        this.dayAggregator = dayAggregator;
    }

    public WeatherAverage getWeatherAverageFor3days(String city) {
        OpenWeatherByCityName5DaysResponse weatherFor5Days = client.getWeatherFor5Days(city);

        Set<Day> days = dayAggregator.aggregateListOfDaysByDay(weatherFor5Days.getList())
                .entrySet().stream()
                .map((entry) -> {
                    List<Day> daysList = entry.getValue();
                    double dailyAverage = getDailyTemperatureAverage(daysList);
                    double nightlyAverage = getNightlyTemperatureAverage(daysList);
                    double pressureAverage = getPressureAverage(daysList);
                    return new Day(dailyAverage, nightlyAverage, pressureAverage, entry.getKey());
                }).sorted().limit(3).collect(toSet());

        return new WeatherAverage(days);
    }

    private double getPressureAverage(List<Day> days) {
        return new HashSet<>(days)
                .stream()
                .mapToDouble(Day::getPressure).average().getAsDouble();
    }

    private double getNightlyTemperatureAverage(List<Day> days) {
        return days.stream()
                .filter(day -> day.getNightlyTemperature() != null)
                .mapToDouble(Day::getNightlyTemperature).average().getAsDouble();
    }

    private double getDailyTemperatureAverage(List<Day> days) {
        return days.stream()
                .filter(day -> day.getDailyTemperature() != null)
                .mapToDouble(Day::getDailyTemperature).average().getAsDouble();
    }
}
