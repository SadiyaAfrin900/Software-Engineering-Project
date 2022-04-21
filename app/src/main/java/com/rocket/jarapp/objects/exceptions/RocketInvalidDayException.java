package com.rocket.jarapp.objects.exceptions;

public class RocketInvalidDayException extends RocketInvalidDateException {
    public RocketInvalidDayException() {
        super("Invalid date was specified");
    }
}
