package com.rocket.jarapp.objects.exceptions;

public class RocketInvalidDateException extends RocketException {
    public RocketInvalidDateException(Exception e) {
        super(e);
    }

    public RocketInvalidDateException(String msg) {
        super(msg);
    }
}
