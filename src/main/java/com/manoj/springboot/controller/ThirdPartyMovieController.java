package com.manoj.springboot.controller;

import com.manoj.springboot.configuration.ApiIntegrationConfig;
import com.manoj.springboot.dto.MovieDto;
import com.manoj.springboot.response.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.function.Consumer;

@RestController
@RequestMapping("/movies")
public class ThirdPartyMovieController {

//    @Autowired
//    ApiIntegrationConfig consumer;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @GetMapping("/fetch/{movieName}")
    public ResponseEntity<?> fetchMovies(@PathVariable String movieName, @RequestHeader("x-api-key")String key){
//        HttpHeaders headers=new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.set("X-RapidAPI-Key",key);
//        headers.set("X-RapidAPI-Host","movie-database-alternative.p.rapidapi.com");
//        HttpEntity request=new HttpEntity(headers);
//        return consumer.consumeApi("https://movie-database-alternative.p.rapidapi.com/?"+"s="+movieName+"&r=json&page=1", HttpMethod.GET,request,new String());

        MovieResponse movieResponse=webClientBuilder.build()
                .get()
                .uri("https://movie-database-alternative.p.rapidapi.com/?"+"s="+movieName+"&r=json&page=1")
                .headers(header->{
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.set("X-RapidAPI-Key",key);
                    header.set("X-RapidAPI-Host","movie-database-alternative.p.rapidapi.com");
                })
                .retrieve()
                .bodyToMono(MovieResponse.class)
                .block();
        return ResponseEntity.ok().body(movieResponse);
    }
}
