package io.weather.service;

import io.weather.domain.WeatherAverage.Day;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static io.weather.client.model.OpenWeatherByCityName5DaysResponse.*;
import static java.time.LocalTime.of;
import static java.util.stream.Collectors.groupingBy;

@Component
public class DayAggregator {

    Map<LocalDate, List<Day>> aggregateListOfDaysByDay(List<Weather> weathers) {
        return weathers
                .stream()
                .map(day -> {
                    Main main = day.getMain();
                    LocalDateTime time = LocalDateTime.parse(day.getDt_txt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime time6 = time.with(of(6, 0));
                    LocalDateTime time18 = time.with(of(18, 0));
                    return createDay(main, time, time6, time18);
                }).collect(groupingBy(Day::getDay));
    }

    private Day createDay(Main main, LocalDateTime time, LocalDateTime time6, LocalDateTime time18) {
        Day dayWithWeather = null;
        if (isDailyHour(time, time6, time18)) {
            dayWithWeather = new Day(main.getTemp(), null, main.getPressure(), time.toLocalDate());
        } else if (isNightlyHour(time, time6, time18)) {
            dayWithWeather = new Day(null, main.getTemp(), main.getPressure(), time.toLocalDate());
        }

        return dayWithWeather;
    }

    private boolean isDailyHour(LocalDateTime time, LocalDateTime time6, LocalDateTime time18) {
        return time.isAfter(time6) && time.isBefore(time18) || time.isEqual(time6) || time.isEqual(time18);
    }

    private boolean isNightlyHour(LocalDateTime time, LocalDateTime time6, LocalDateTime time18) {
        return time.isAfter(time18) || time.isBefore(time6);
    }
}
