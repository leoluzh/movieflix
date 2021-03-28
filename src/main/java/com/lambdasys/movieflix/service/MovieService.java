package com.lambdasys.movieflix.service;

import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.entity.Movie;
import com.lambdasys.movieflix.exceptions.MovieAlreadyRegisteredException;
import com.lambdasys.movieflix.exceptions.MovieNotFoundException;
import com.lambdasys.movieflix.exceptions.MovieScoreExceededException;
import com.lambdasys.movieflix.mapper.MovieMapper;
import com.lambdasys.movieflix.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper = MovieMapper.INSTANCE;

    public MovieService(@Autowired final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieDTO createMovie(final MovieDTO movieDTO) throws MovieAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(movieDTO.getId());
        verifyIfIsAlreadyRegistered(movieDTO.getName());
        final Movie movie = movieMapper.toModel(movieDTO);
        final Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toDTO(savedMovie);
    }

    public MovieDTO findByName(final String name) throws MovieNotFoundException {
        final Movie foundMovie = movieRepository.findByName(name)
                .orElseThrow(() -> new MovieNotFoundException(name));
        return movieMapper.toDTO(foundMovie);
    }

    public List<MovieDTO> listAll() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(final Long id) throws MovieNotFoundException {
        verifyIfExists(id);
        movieRepository.deleteById(id);
    }

    public MovieDTO views(final Long id) throws MovieNotFoundException {
        final Movie movieToIncrementViews = verifyIfExists(id);
        movieToIncrementViews.setViews(movieToIncrementViews.getViews() + 1);
        movieRepository.save(movieToIncrementViews);
        return movieMapper.toDTO(movieToIncrementViews);
    }

    public MovieDTO like(final Long id) throws MovieNotFoundException {
        final Movie movieToIncrementLikes = verifyIfExists(id);
        movieToIncrementLikes.setLikes(movieToIncrementLikes.getLikes() + 1);
        movieRepository.save(movieToIncrementLikes);
        return movieMapper.toDTO(movieToIncrementLikes);
    }

    public MovieDTO dislike(final Long id) throws MovieNotFoundException {
        final Movie movieToIncrementDislikes = verifyIfExists(id);
        movieToIncrementDislikes.setDislikes(movieToIncrementDislikes.getDislikes() + 1);
        movieRepository.save(movieToIncrementDislikes);
        return movieMapper.toDTO(movieToIncrementDislikes);
    }

    public Movie verifyIfExists(final Long id) throws MovieNotFoundException {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    public void verifyIfIsAlreadyRegistered(final String name) throws MovieAlreadyRegisteredException {
        final Optional<Movie> savedMovie = movieRepository.findByName(name);
        if (savedMovie.isPresent()) {
            throw new MovieAlreadyRegisteredException(name);
        }
    }

    public void verifyIfIsAlreadyRegistered(final Long id) throws MovieAlreadyRegisteredException {
        final Optional<Movie> savedMovie = movieRepository.findById(id);
        if (savedMovie.isPresent()) {
            throw new MovieAlreadyRegisteredException(id);
        }
    }


    public MovieDTO increment(final Long id, final Integer quantityToIncrement) throws MovieNotFoundException, MovieScoreExceededException {
        final Movie movieToIncrementScore = verifyIfExists(id);
        final Integer quantityAfterIncrement = quantityToIncrement + movieToIncrementScore.getScore();
        if (quantityAfterIncrement <= movieToIncrementScore.getMax()) {
            movieToIncrementScore.setScore(quantityAfterIncrement);
            Movie incrementedMovieStock = movieRepository.save(movieToIncrementScore);
            return movieMapper.toDTO(incrementedMovieStock);
        }
        throw new MovieScoreExceededException(id, quantityToIncrement);
    }

    public MovieDTO decrement(final Long id, final Integer quantityToDecrement) throws MovieNotFoundException, MovieScoreExceededException {
        final Movie movieToDecrementScore = verifyIfExists(id);
        final int quantityAfterDecrement =  movieToDecrementScore.getScore() - quantityToDecrement;
        if (quantityAfterDecrement > 0) {
            movieToDecrementScore.setScore(quantityAfterDecrement);
            Movie decrementedMovieStock = movieRepository.save(movieToDecrementScore);
            return movieMapper.toDTO(decrementedMovieStock);
        }
        throw new MovieScoreExceededException(id, quantityToDecrement);
    }


}
