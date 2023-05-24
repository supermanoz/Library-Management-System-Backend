package com.manoj.springboot.controller;

import com.manoj.springboot.model.Request;
import com.manoj.springboot.response.MyResponse;
import com.manoj.springboot.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    RequestService requestService;
    @GetMapping("/fetchAll")
    public ResponseEntity<?> fetchAll(){
        List<Request> requests=requestService.fetchAll();
        return new ResponseEntity<>(new MyResponse<>(HttpStatus.OK,requests),HttpStatus.OK);
    }
}
