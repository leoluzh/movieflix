package com.lambdasys.movieflix.service;

import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.entity.Movie;
import com.lambdasys.movieflix.exceptions.MovieAlreadyRegisteredException;
import com.lambdasys.movieflix.exceptions.MovieNotFoundException;
import com.lambdasys.movieflix.mapper.MovieMapper;
import com.lambdasys.movieflix.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper = MovieMapper.INSTANCE;

    public MovieService( @Autowired MovieRepository movieRepository ) {
        this.movieRepository = movieRepository;
    }

    public MovieDTO createMovie( MovieDTO movieDTO ) throws MovieAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered( movieDTO.getName() );
        Movie movie = movieMapper.toModel( movieDTO );
        Movie savedMovie = movieRepository.save( movie );
        return movieMapper.toDTO( savedMovie );
    }

    public Movie verifyIfExists( Long id ) throws MovieNotFoundException {
        return movieRepository.findById( id )
                .orElseThrow( () -> new MovieNotFoundException( id ) );
    }

    public void verifyIfIsAlreadyRegistered( String name ) throws MovieAlreadyRegisteredException {
        Optional<Movie> savedMovie = movieRepository.findByName( name );
        if ( savedMovie.isPresent() ){
            throw new MovieAlreadyRegisteredException( name );
        }
    }

}
