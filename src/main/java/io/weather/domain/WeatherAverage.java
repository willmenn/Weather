package io.weather.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WeatherAverage {
    private Set<Day> days;

    @AllArgsConstructor
    @Getter
    public static class Day implements Comparable<Day> {
        private Double dailyTemperature;
        private Double nightlyTemperature;
        private Double pressure;
        private LocalDate day;

        @Override
        public boolean equals(Object dayToCompare) {
            return day.isEqual((LocalDate) dayToCompare);
        }

        @Override
        public int compareTo(Day dayToCompare) {
            return this.getDay().compareTo(dayToCompare.getDay());
        }
    }
}