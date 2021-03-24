package com.lambdasys.movieflix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends  Exception {

    public MovieNotFoundException( String name ){
        super(String.format("Movie with name % not found in the system.",name));
    }

    public MovieNotFoundException( Long id ){
        super(String.format("Movie with id % not found in the system.",id));
    }

}
