package it.intesys.rookie.controller;

import it.intesys.rookie.service.Mandatory;
import it.intesys.rookie.service.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RookieExceptionHandler {
    @ExceptionHandler (NotFound.class)
    public ResponseEntity<String> handleNotFound (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler (Mandatory.class)
    public ResponseEntity<String> handleBadRequest (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
