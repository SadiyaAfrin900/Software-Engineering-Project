package com.rocket.jarapp.objects.exceptions;

public class RocketDatabaseWriteException extends RocketRuntimeException {
    public RocketDatabaseWriteException(Exception cause) {
        super(cause);
    }
}
