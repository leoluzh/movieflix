package com.lambdasys.movieflix.controller;

import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.dto.ScoreQuantityDTO;
import com.lambdasys.movieflix.exceptions.MovieAlreadyRegisteredException;
import com.lambdasys.movieflix.exceptions.MovieNotFoundException;
import com.lambdasys.movieflix.exceptions.MovieScoreExceededException;
import com.lambdasys.movieflix.service.MovieService;
//import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author leoluzh
 * @version 0.1
 *
 * MovieController é uma interface que prove métodos para gerenciar filmes.
 */


@RestController
@RequestMapping("/api/v1/movies")
//Does not working with jdk 16 - looking add-open for this problem
//@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieController implements MovieControllerDocs {

    private final MovieService movieService;

    public MovieController(@Autowired final MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public MovieDTO createMovie(@RequestBody @Valid final MovieDTO movieDTO) throws MovieAlreadyRegisteredException {
        return movieService.createMovie(movieDTO);
    }

    @GetMapping("/{name}")
    @Override
    public MovieDTO findByName(@PathVariable final String name) throws MovieNotFoundException {
        return movieService.findByName(name);
    }

    @GetMapping
    @Override
    public List<MovieDTO> listMovies() {
        return movieService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable final Long id) throws MovieNotFoundException {
        movieService.deleteById(id);
    }

    @GetMapping("/{id}/view")
    @Override
    public MovieDTO views(@PathVariable final Long id) throws MovieNotFoundException {
        return movieService.views(id);
    }

    @GetMapping("/{id}/like")
    @Override
    public MovieDTO like(@PathVariable final Long id) throws MovieNotFoundException {
        return movieService.like(id);
    }

    @GetMapping("/{id}/dislike")
    @Override
    public MovieDTO dislike(@PathVariable final Long id) throws MovieNotFoundException {
        return movieService.dislike(id);
    }

    @PatchMapping("/{id}/increment")
    @Override
    public MovieDTO increment(@PathVariable final Long id, @RequestBody @Valid final ScoreQuantityDTO scoreQuantityDTO) throws MovieNotFoundException, MovieScoreExceededException {
        return movieService.increment(id, scoreQuantityDTO.getQuantity());
    }

    @PatchMapping("/{id}/decrement")
    @Override
    public MovieDTO decrement(@PathVariable final Long id, @RequestBody @Valid final ScoreQuantityDTO scoreQuantityDTO) throws MovieNotFoundException , MovieScoreExceededException {
        return movieService.decrement(id, scoreQuantityDTO.getQuantity());
    }

}
