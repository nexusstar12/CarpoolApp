package com.fantasticfour.poolapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        // Logs the exception with details
        logger.error("An unexpected error occurred: ", ex);

        // Custom response body or just return message here
        return new ResponseEntity<>("An internal error has occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Add more methods here to handle different types of exceptions
}
