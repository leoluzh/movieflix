package com.lambdasys.movieflix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class MovieAlreadyRegisteredException extends Exception {

    public MovieAlreadyRegisteredException( String name ){
        super(String.format("Movie with name %s already registered in the system.",name));
    }

}


