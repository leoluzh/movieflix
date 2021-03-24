package com.lambdasys.movieflix.service;

import com.lambdasys.movieflix.builder.MovieDTOBuilder;
import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.entity.Movie;
import com.lambdasys.movieflix.exceptions.MovieAlreadyRegisteredException;
import com.lambdasys.movieflix.mapper.MovieMapper;
import com.lambdasys.movieflix.repository.MovieRepository;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    private static final Long INVALID_MOVIE_ID = 0L;

    private MovieMapper movieMapper = MovieMapper.INSTANCE;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void before(){

    }

    @AfterEach
    public void after(){

    }

    @DisplayName("When movie is informed then it should be created")
    @Test
    public void whenMovieIsInformedThenItShouldBeCreated() throws MovieAlreadyRegisteredException {

        // given
        MovieDTO expectedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedSaveMovie = movieMapper.toModel( expectedMovieDTO );

        // when - stubing mock
        when(movieRepository.findByName(expectedMovieDTO.getName())).thenReturn(Optional.empty());
        when(movieRepository.save(expectedSaveMovie)).thenReturn(expectedSaveMovie);

        // then
        MovieDTO createdMovieDTO = movieService.createMovie( expectedMovieDTO );

        assertThat( createdMovieDTO.getId() , Matchers.is(  Matchers.equalTo( expectedMovieDTO.getId() ) ) );
        assertThat( createdMovieDTO.getId() , Matchers.is(  Matchers.equalTo( expectedMovieDTO.getId() ) ) );
        assertThat( createdMovieDTO.getId() , Matchers.is(  Matchers.equalTo( expectedMovieDTO.getId() ) ) );

    }

    @DisplayName("When an already registered movie is informed then an exception should be thrown")
    @Test
    public void whenAlreadyRegisteredMovieIsInformedThenAnExceptionShouldBeThrow(){

        // given
        MovieDTO expectedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie duplicatedMovie = movieMapper.toModel( expectedMovieDTO );

        // when
        Mockito.when(movieRepository.findByName(expectedMovieDTO.getName())).thenReturn(Optional.of(duplicatedMovie));

        // then
        assertThrows( MovieAlreadyRegisteredException.class , () -> movieService.createMovie(expectedMovieDTO) );

    }

}
