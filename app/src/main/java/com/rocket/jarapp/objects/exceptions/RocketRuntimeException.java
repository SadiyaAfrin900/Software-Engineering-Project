package com.rocket.jarapp.objects.exceptions;

public class RocketRuntimeException extends RuntimeException{

    public RocketRuntimeException(final Exception cause) {
        super(cause);
    }

    public RocketRuntimeException(String message) {
        super(message);
    }
}
