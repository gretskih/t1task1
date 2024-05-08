package ru.t1.aophome.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.t1.aophome.dto.ErrorMessage;
import ru.t1.aophome.exception.ControllerException;

@RestControllerAdvice(
        assignableTypes = {TrackTimeController.class,
                UrlController.class}
)
public class ErrorHandlerController {

    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<ErrorMessage> onHandleException(ControllerException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorMessage(e.getCause().toString(), e.getCause().getCause().getMessage()));
    }
}
