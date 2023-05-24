package com.manoj.springboot.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class MyResponse<T> {
    private int statusCode;
    private String status;
    private T payload;

    public MyResponse(HttpStatus httpStatus,T t){
        this.statusCode=httpStatus.value();
        this.status=httpStatus.name();
        this.payload=t;
    }

    public MyResponse(T t){
        this.statusCode=HttpStatus.OK.value();
        this.status=HttpStatus.OK.name();
        this.payload=t;
    }
}
