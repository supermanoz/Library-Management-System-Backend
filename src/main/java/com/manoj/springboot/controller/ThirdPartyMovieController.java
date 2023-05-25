package com.manoj.springboot.controller;

import com.manoj.springboot.configuration.ApiIntegrationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/movies")
public class ThirdPartyMovieController {

    @Autowired
    ApiIntegrationConfig consumer;
    @GetMapping("/fetch/{movieName}")
    public ResponseEntity<?> fetchMovies(@PathVariable String movieName, @RequestHeader("x-api-key")String key){
        HttpHeaders headers=new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-RapidAPI-Key",key);
        headers.set("X-RapidAPI-Host","movie-database-alternative.p.rapidapi.com");
        HttpEntity request=new HttpEntity(headers);
        return consumer.consumeApi("https://movie-database-alternative.p.rapidapi.com/?"+"s="+movieName+"&r=json&page=1", HttpMethod.GET,request,new String());
    }
}
