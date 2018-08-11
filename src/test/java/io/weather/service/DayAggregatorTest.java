package io.weather.service;


import io.weather.client.model.OpenWeatherByCityName5DaysResponse;
import io.weather.domain.WeatherAverage.Day;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static io.weather.client.model.OpenWeatherByCityName5DaysResponse.*;
import static org.junit.Assert.assertEquals;

public class DayAggregatorTest {

    @Test
    public void shouldBeAbleToAggregateByDayGivenAListOfDays() {
        String day1 = "2018-08-07 06:00:00";
        String day2 = "2018-08-08 20:00:00";
        List<Weather> weathers = newArrayList(new Weather(new Main(1.0, 100.0), "2018-08-08 03:00:00"),
                new Weather(new Main(2.0, 100.0), day1),
                new Weather(new Main(1.0, 100.0), day2),
                new Weather(new Main(2.0, 100.0), "2018-08-07 12:00:00"),
                new Weather(new Main(2.0, 100.0), "2018-08-07 18:00:00"));

        DayAggregator aggregator = new DayAggregator();
        Map<LocalDate, List<Day>> response = aggregator.aggregateListOfDaysByDay(weathers);
        LocalDate time1 = LocalDateTime.parse(day1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
        LocalDate time2 = LocalDateTime.parse(day2, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();

        assertEquals(3, response.get(time1).size());
        assertEquals(2, response.get(time2).size());
    }
}