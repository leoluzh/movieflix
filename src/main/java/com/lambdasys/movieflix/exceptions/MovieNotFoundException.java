package com.lambdasys.movieflix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends  Exception {

    public MovieNotFoundException(final String name) {
        super(String.format("Movie with name %s not found in the system.", name));
    }

    public MovieNotFoundException(final Long id) {
        super(String.format("Movie with id %s not found in the system.", id));
    }

}
