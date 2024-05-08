package ru.t1.aophome.exception;

public class ControllerException extends Exception {
    public ControllerException(String message, Exception exception) {
        super(message, exception);
    }
}
