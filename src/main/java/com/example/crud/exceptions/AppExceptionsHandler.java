package com.example.crud.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value= {Exception.class})
    public ResponseEntity<?> handleAnyExceptions(Exception e, WebRequest request) {
        String errorMessageException = "Internal Server Error";
        if(e.getLocalizedMessage()!= null) {
            errorMessageException += ": " + e.getLocalizedMessage();
        }else {
            errorMessageException += ": " + e.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageException);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value= {NullPointerException.class})
    public ResponseEntity<?> handleNullPointerException(NullPointerException e, WebRequest request) {
        String errorMessageException = "Null Pointer Exception";
        if(e.getLocalizedMessage()!= null) {
            errorMessageException += ": " + e.getLocalizedMessage();
        }else {
            errorMessageException += ": " + e.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageException);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
