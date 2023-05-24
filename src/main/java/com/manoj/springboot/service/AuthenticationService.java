package com.manoj.springboot.service;

import com.manoj.springboot.security.AuthenticationRequest;
import com.manoj.springboot.security.AuthenticationResponse;

public interface AuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws Exception;
}
