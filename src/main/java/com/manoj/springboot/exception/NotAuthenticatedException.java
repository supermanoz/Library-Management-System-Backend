package com.manoj.springboot.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class NotAuthenticatedException extends RuntimeException{
    private Integer errorCode;
    private String errorMessage;

    public NotAuthenticatedException(String errorMessage){
        this.errorCode= HttpStatus.BAD_REQUEST.value();
        this.errorMessage=errorMessage;
    }
}
