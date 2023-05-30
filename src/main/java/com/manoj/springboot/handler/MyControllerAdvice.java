package com.manoj.springboot.handler;

import com.manoj.springboot.exception.AlreadyExistsException;
import com.manoj.springboot.exception.DoesNotExistException;
import com.manoj.springboot.exception.EmptyFieldException;
import com.manoj.springboot.exception.NotAuthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<String> emptyFieldExceptionHandler(EmptyFieldException emptyFieldException){
        return new ResponseEntity<String>(emptyFieldException.getErrorMessage(),emptyFieldException.getErrorCode());
    }

    @ExceptionHandler(DoesNotExistException.class)
    public ResponseEntity<String> doesNotExistExceptionHandler(DoesNotExistException doesNotExistException){
        return new ResponseEntity<String>(doesNotExistException.getErrorMessage(),doesNotExistException.getErrorCode());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> alreadyExistsExceptionHandler(AlreadyExistsException alreadyExistsException){
        return new ResponseEntity<String>(alreadyExistsException.getErrorMessage(),alreadyExistsException.getErrorCode());
    }

    @ExceptionHandler(NotAuthenticatedException.class)
    public ResponseEntity<?> notAuthenticatedExceptionHandler(NotAuthenticatedException notAuthenticatedException){
        return new ResponseEntity<>(notAuthenticatedException.getErrorMessage(),notAuthenticatedException.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> defaultExceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
