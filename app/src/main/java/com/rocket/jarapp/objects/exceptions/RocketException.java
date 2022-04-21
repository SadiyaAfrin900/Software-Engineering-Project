package com.rocket.jarapp.objects.exceptions;

public class RocketException extends Exception {
    public RocketException(Exception e) {
        super(e);
    }

    public RocketException(String msg) {
        super(msg);
    }
}
