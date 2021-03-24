package com.lambdasys.movieflix.service;

import com.lambdasys.movieflix.builder.MovieDTOBuilder;
import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.entity.Movie;
import com.lambdasys.movieflix.exceptions.MovieAlreadyRegisteredException;
import com.lambdasys.movieflix.mapper.MovieMapper;
import com.lambdasys.movieflix.repository.MovieRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        Mockito.when(movieRepository.findByName(expectedMovieDTO.getName())).thenReturn(Optional.empty());
        Mockito.when(movieRepository.save(expectedSaveMovie)).thenReturn(expectedSaveMovie);

        // then
        MovieDTO createdMovieDTO = movieService.createMovie( expectedMovieDTO );

        MatcherAssert.assertThat( createdMovieDTO.getId() , Matchers.is(  Matchers.equalTo( expectedMovieDTO.getId() ) ) );
        MatcherAssert.assertThat( createdMovieDTO.getId() , Matchers.is(  Matchers.equalTo( expectedMovieDTO.getId() ) ) );
        MatcherAssert.assertThat( createdMovieDTO.getId() , Matchers.is(  Matchers.equalTo( expectedMovieDTO.getId() ) ) );

    }


}
