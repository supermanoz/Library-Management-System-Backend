package com.manoj.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Indexed;

@Configuration
@PropertySource("classpath:message.properties")
@Data
@AllArgsConstructor
public class AlreadyExistsException extends RuntimeException{

    @Value("${alreadyExists}")
    private String message;

    private String errorMessage;
    private HttpStatus errorCode;
    public AlreadyExistsException(String errorMessage){
        this.errorMessage=errorMessage;
        this.errorCode=HttpStatus.BAD_REQUEST;
    }

    public AlreadyExistsException(){
        this.errorCode=HttpStatus.BAD_REQUEST;
    }
}
