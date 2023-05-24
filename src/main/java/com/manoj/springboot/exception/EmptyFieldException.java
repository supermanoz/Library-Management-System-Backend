package com.manoj.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
public class EmptyFieldException extends RuntimeException{

    private HttpStatus errorCode;
    private String errorMessage;
    public EmptyFieldException(String errorMessage){
        this.errorMessage=errorMessage;
        this.errorCode=HttpStatus.BAD_REQUEST;
    }
}
