package com.lambdasys.movieflix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MovieAlreadyRegisteredException extends Exception {

    public MovieAlreadyRegisteredException(final String name) {
        super(String.format("Movie with name %s already registered in the system.", name));
    }

    public MovieAlreadyRegisteredException(final Long id) {
        super(String.format("Movie with id %s already registered in the system.", id));
    }

}


