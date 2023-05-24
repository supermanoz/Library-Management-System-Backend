package com.manoj.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class DoesNotExistException extends RuntimeException{

    private String errorMessage;
    private HttpStatus errorCode;
    public DoesNotExistException(String errorMessage){
        this.errorMessage=errorMessage;
        this.errorCode=HttpStatus.BAD_REQUEST;
    }

    public DoesNotExistException(){
        this.errorCode=HttpStatus.BAD_REQUEST;
        this.errorMessage=HttpStatus.BAD_REQUEST.name();
    }
}
