package com.manoj.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiIntegrationConfig<T> {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public ResponseEntity<?> consumeApi(String url, HttpMethod httpMethod, HttpEntity request,T t){
        return restTemplate().exchange(url,httpMethod,request,t.getClass());
    }
}
