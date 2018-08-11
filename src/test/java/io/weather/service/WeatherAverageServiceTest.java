package io.weather.service;

import io.weather.client.OpenWeatherMapClient;
import io.weather.client.model.OpenWeatherByCityName5DaysResponse;
import io.weather.domain.WeatherAverage;
import io.weather.domain.WeatherAverage.Day;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WeatherAverageServiceTest {

    @Mock
    private OpenWeatherMapClient client;

    @Mock
    private DayAggregator aggregator;

    @InjectMocks
    private WeatherAverageService service;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldBeAbleToCalculateTheAverageFor3DaysFromToday() {
        Map<LocalDate, List<Day>> map = new HashMap<>();
        LocalDate date = LocalDate.of(2018, 8, 8);
        List<Day> days = newArrayList(new Day(2.0, null, 100.0, date),
                new Day(2.0, null, 100.0, date),
                new Day(null, 1.0, 100.0, date),
                new Day(null, 1.0, 100.0, date));
        map.put(date, days);

        when(aggregator.aggregateListOfDaysByDay(any())).thenReturn(map);

        String anyCity = "AnyCity";
        OpenWeatherByCityName5DaysResponse openWeatherResponse = OpenWeatherByCityName5DaysResponse
                .builder().list(newArrayList()).build();
        when(client.getWeatherFor5Days(eq(anyCity))).thenReturn(openWeatherResponse);

        WeatherAverage response = service.getWeatherAverageFor3days(anyCity);

        Day day = response.getDays().stream().findFirst().get();

        assertEquals(2.0, day.getDailyTemperature(), 0.1);
        assertEquals(1.0, day.getNightlyTemperature(), 0.1);
        assertEquals(100.0, day.getPressure(), 0.1);
    }
}