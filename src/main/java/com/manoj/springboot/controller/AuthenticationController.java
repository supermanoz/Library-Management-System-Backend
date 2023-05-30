package com.manoj.springboot.controller;

import com.manoj.springboot.security.AuthenticationRequest;
import com.manoj.springboot.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    Logger logger= LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            return new ResponseEntity<>(authenticationService.authenticate(authenticationRequest), HttpStatus.OK);
        }catch(Exception e){
            logger.trace("hello baby exception occured during authentication!"+e);
        }
        return new ResponseEntity<>("Bad Credentails baby!",HttpStatus.OK);
    }
}
