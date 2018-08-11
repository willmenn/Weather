package io.weather.client.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NotFoundCityException extends RuntimeException {

    public NotFoundCityException() {
        super("Could not find the city.");
    }
}
