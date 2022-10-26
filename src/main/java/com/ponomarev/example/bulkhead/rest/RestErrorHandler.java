package com.ponomarev.example.bulkhead.rest;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Advice for rest controllers
 * @author Egor Ponomarev
 */
@RestControllerAdvice
public class RestErrorHandler {
    @ExceptionHandler(BulkheadFullException.class)
    public ResponseEntity<String> bulkheadFullException(BulkheadFullException exception) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
            .body(exception.getMessage());
    }
}
