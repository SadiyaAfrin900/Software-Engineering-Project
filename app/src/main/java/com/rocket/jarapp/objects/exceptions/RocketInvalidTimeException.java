package com.rocket.jarapp.objects.exceptions;

public class RocketInvalidTimeException extends RocketException {
    public RocketInvalidTimeException() {
        super("Specified time is invalid");
    }
}
