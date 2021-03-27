package com.lambdasys.movieflix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MovieScoreExceedException extends Exception {

    public MovieScoreExceedException(Long id , Integer quantityToIncrement){
        super(String.format("Movie with id %s to increment informed exceeds the max score: %s",id,quantityToIncrement));
    }

}
