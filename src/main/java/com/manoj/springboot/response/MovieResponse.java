package com.manoj.springboot.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.manoj.springboot.dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResponse {
    @JsonProperty("Search")
    ArrayList<MovieDto> search;
    Integer totalResults;
    @JsonProperty("Response")
    String response;
}
