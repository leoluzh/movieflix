package com.lambdasys.movieflix.controller;

import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.exceptions.MovieAlreadyRegisteredException;
import com.lambdasys.movieflix.exceptions.MovieNotFoundException;
import com.lambdasys.movieflix.service.MovieService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/movies")
//Does not working with jdk 16 - looking add-open for this problem
//@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieController implements MovieControllerDocs {

    private final MovieService movieService;

    public MovieController( @Autowired MovieService movieService ){
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public MovieDTO createMovie( @RequestBody @Valid MovieDTO movieDTO) throws MovieAlreadyRegisteredException {
        return movieService.createMovie(movieDTO);
    }

    @GetMapping("/{name}")
    @Override
    public MovieDTO findByName(String name) throws MovieNotFoundException {
        throw new UnsupportedOperationException("Find by name not implemenent yet.");
    }

    @GetMapping
    @Override
    public List<MovieDTO> listMovies() {
        throw new UnsupportedOperationException("List movies not implemenent yet.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) throws MovieNotFoundException {
        throw new UnsupportedOperationException("Delete by id not implemenent yet.");
    }

    @GetMapping("/{id}/like")
    @Override
    public void like( @PathVariable Long id) {
        throw new UnsupportedOperationException("Delete by id not implemenent yet.");
    }

    @GetMapping("/{id}/dislike")
    @Override
    public void dislike( @PathVariable Long id) {
        throw new UnsupportedOperationException("Delete by id not implemenent yet.");
    }

}
