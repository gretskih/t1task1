package ru.t1.aophome.exception;

public class ServiceException extends Exception {
    public ServiceException(String message, Exception exception) {
        super(message, exception);
    }
}
