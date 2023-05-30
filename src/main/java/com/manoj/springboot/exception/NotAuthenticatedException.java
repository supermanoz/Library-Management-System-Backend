package com.manoj.springboot.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class NotAuthenticatedException extends RuntimeException{
    private HttpStatus errorCode;
    private String errorMessage;

    public NotAuthenticatedException(String errorMessage){
        this.errorCode= HttpStatus.BAD_REQUEST;
        this.errorMessage=errorMessage;
    }
}
