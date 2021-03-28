package com.lambdasys.movieflix.service;

import com.lambdasys.movieflix.builder.MovieDTOBuilder;
import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.dto.ScoreQuantityDTO;
import com.lambdasys.movieflix.entity.Movie;
import com.lambdasys.movieflix.exceptions.MovieAlreadyRegisteredException;
import com.lambdasys.movieflix.exceptions.MovieNotFoundException;
import com.lambdasys.movieflix.exceptions.MovieScoreExceededException;
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

import java.util.Collections;
import java.util.List;
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

    @Order(1)
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

    @Order(2)
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

    @Order(3)
    @DisplayName("When a valid movie name is given the return an movie")
    @Test
    public void whenValidMovieNameIsGivenThenReturnAnMovie() throws MovieNotFoundException {

        // given
        MovieDTO expectedFoundMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedFoundMovie = movieMapper.toModel( expectedFoundMovieDTO );

        // when
        when(movieRepository.findByName(expectedFoundMovie.getName())).thenReturn(Optional.of(expectedFoundMovie));

        // then
        MovieDTO foundMovieDTO = movieService.findByName(expectedFoundMovieDTO.getName());

        assertThat(foundMovieDTO,is(equalTo(expectedFoundMovieDTO)));

    }

    @Order(4)
    @DisplayName("When not registered movie name is given then throw an exception")
    @Test
    public void whenNotRegisteredMovieNameIsGivenThenThrowAnException(){

        // given
        MovieDTO expectedFoundMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieRepository.findByName(expectedFoundMovieDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(MovieNotFoundException.class, () -> movieService.findByName(expectedFoundMovieDTO.getName()));

    }

    @Order(5)
    @DisplayName("When list movie is called then return a list of movies")
    @Test
    public void whenListMovieIsCalledThenReturnAListOfMovies(){

        // given -
        MovieDTO expectedFoundMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedFoundMovie = movieMapper.toModel( expectedFoundMovieDTO );

        // when
        when(movieRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundMovie));

        // then
        List<MovieDTO> foundListMoviesDTO = movieService.listAll();

        assertThat(foundListMoviesDTO, is(not(empty())));
        assertThat(foundListMoviesDTO.get(0), is(equalTo(expectedFoundMovieDTO)));

    }

    @Order(6)
    @DisplayName("When list movie is called then return an empty list of movies")
    @Test
    public void whenListMovieIsCalledThenReturnAnEmptyListOfMovies(){

        // when
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<MovieDTO> foundListMoviesDTO = movieService.listAll();

        assertThat(foundListMoviesDTO,is(empty()));

    }

    @Order(7)
    @DisplayName("When delete is called with a valid id then a movie should be deleted")
    @Test
    public void whenDeleteIsCalledWithValidIdThenMovieShouldBeDeleted() throws MovieNotFoundException {

        // given
        MovieDTO expectedDeletedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedDeleteMovie = movieMapper.toModel( expectedDeletedMovieDTO );

        // when
        when(movieRepository.findById(expectedDeletedMovieDTO.getId())).thenReturn(Optional.of(expectedDeleteMovie));

        // then
        movieService.deleteById(expectedDeletedMovieDTO.getId());

        //how to check a void/one way function - use times to check if pass
        verify(movieRepository,times(1)).findById(expectedDeletedMovieDTO.getId());
        verify(movieRepository,times(1)).deleteById(expectedDeletedMovieDTO.getId());

    }

    @Order(8)
    @DisplayName("When delete is called with a invalid id then an exception should be throw")
    @Test
    public void whenDeleteIsCalledWithInvalidIdThenExceptionShouldThrow() throws MovieNotFoundException {

        // given
        MovieDTO expectedDeletedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieRepository.findById(expectedDeletedMovieDTO.getId())).thenReturn(Optional.empty());

        // then
        assertThrows(MovieNotFoundException.class, () -> movieService.deleteById(expectedDeletedMovieDTO.getId()));

    }

    @Order(9)
    @DisplayName("When viewed is called then increment the number of views")
    @Test
    public void whenViewsIsCalledThenIncrementNumberOfViews() throws MovieNotFoundException {

        // given
        MovieDTO expectedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedMovie = movieMapper.toModel( expectedMovieDTO );

        // when
        when(movieRepository.findById(expectedMovieDTO.getId())).thenReturn(Optional.of(expectedMovie));
        when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        Long expectedViewsAfterIncrement = expectedMovieDTO.getViews() + 1 ;

        // then
        MovieDTO viewsMoviesDTO = movieService.views(expectedMovieDTO.getId());

        assertThat(expectedViewsAfterIncrement,equalTo(viewsMoviesDTO.getViews()));

    }

    @Order(10)
    @DisplayName("When liked is called then increment the number of likes")
    @Test
    public void whenLikeIsCalledThenIncrementNumberOfLikes() throws MovieNotFoundException {

        // given
        MovieDTO expectedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedMovie = movieMapper.toModel( expectedMovieDTO );

        // when
        when(movieRepository.findById(expectedMovieDTO.getId())).thenReturn(Optional.of(expectedMovie));
        when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        Long expectedLikesAfterIncrement = expectedMovieDTO.getLikes() + 1 ;

        // then
        MovieDTO likedMovieDTO = movieService.like(expectedMovieDTO.getId());

        assertThat(expectedLikesAfterIncrement,equalTo(likedMovieDTO.getLikes()));


    }

    @Order(11)
    @DisplayName("When disliked is called then increment the number of dislikes")
    @Test
    public void whenDislikeIsCalledThenIncrementNumberOfLikes() throws MovieNotFoundException {

        // given
        MovieDTO expectedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedMovie = movieMapper.toModel( expectedMovieDTO );

        // when
        when(movieRepository.findById(expectedMovieDTO.getId())).thenReturn(Optional.of(expectedMovie));
        when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        Long expectedDislikesAfterIncrement = expectedMovieDTO.getDislikes() + 1 ;

        // then
        MovieDTO dislikedMoviesDTO = movieService.dislike(expectedMovieDTO.getId());

        assertThat(expectedDislikesAfterIncrement,equalTo(dislikedMoviesDTO.getDislikes()));

    }

    @Order(12)
    @DisplayName("When increment score is called then increment the number of scores")
    @Test
    public void whenIncrementScoreIsCalledThenIncrementNumberOfScores() throws MovieNotFoundException, MovieScoreExceededException {

        // given
        MovieDTO expectedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedMovie = movieMapper.toModel( expectedMovieDTO );
        ScoreQuantityDTO quantityDTO = ScoreQuantityDTO.builder().quantity(10).build();

        // when
        when(movieRepository.findById(expectedMovieDTO.getId())).thenReturn(Optional.of(expectedMovie));
        when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        Integer expectedScoreAfterIncrement = expectedMovieDTO.getScore() + quantityDTO.getQuantity();

        // then
        MovieDTO incrementedMoviesDTO = movieService.increment(expectedMovieDTO.getId(),quantityDTO.getQuantity());

        assertThat(expectedScoreAfterIncrement,equalTo(incrementedMoviesDTO.getScore()));

    }

    @Order(13)
    @DisplayName("When increment score with an exceeded quantity is called then throw an exception")
    @Test
    public void whenIncrementScoreWithAnExceededIsCalledThenIncrementNumberOfScores() throws MovieNotFoundException {

        // given
        MovieDTO expectedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedMovie = movieMapper.toModel( expectedMovieDTO );
        ScoreQuantityDTO quantityDTO = ScoreQuantityDTO.builder().quantity(expectedMovieDTO.getMax()+1).build();

        // when
        when(movieRepository.findById(expectedMovieDTO.getId())).thenReturn(Optional.of(expectedMovie));
        //when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        Integer expectedScoreAfterIncrement = expectedMovieDTO.getScore()  + quantityDTO.getQuantity();

        // then
        assertThrows(MovieScoreExceededException.class, () -> movieService.increment(expectedMovieDTO.getId(),quantityDTO.getQuantity()));

    }

    @Order(14)
    @DisplayName("When decrement score is called then decrement the number of scores")
    @Test
    public void whenDecrementScoreIsCalledThenDecrementNumberOfScores() throws MovieNotFoundException, MovieScoreExceededException {

        // given
        MovieDTO expectedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedMovie = movieMapper.toModel( expectedMovieDTO );
        ScoreQuantityDTO quantityDTO = ScoreQuantityDTO.builder().quantity(2).build();

        // when
        when(movieRepository.findById(expectedMovieDTO.getId())).thenReturn(Optional.of(expectedMovie));
        when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        Integer expectedScoreAfterDecrement = expectedMovieDTO.getScore() - quantityDTO.getQuantity();

        // then
        MovieDTO decrementedMoviesDTO = movieService.decrement(expectedMovieDTO.getId(),quantityDTO.getQuantity());

        assertThat(expectedScoreAfterDecrement,equalTo(decrementedMoviesDTO.getScore()));

    }

    @Order(15)
    @DisplayName("When decrement score with an exceeded quantity is called then throw an exception")
    @Test
    public void whenDecrementScoreWithAnExceededIsCalledThenDecrementNumberOfScores() throws MovieNotFoundException {

        // given
        MovieDTO expectedMovieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        Movie expectedMovie = movieMapper.toModel( expectedMovieDTO );
        ScoreQuantityDTO quantityDTO = ScoreQuantityDTO.builder().quantity(expectedMovieDTO.getScore()+1).build();

        // when
        when(movieRepository.findById(expectedMovieDTO.getId())).thenReturn(Optional.of(expectedMovie));
        //when(movieRepository.save(expectedMovie)).thenReturn(expectedMovie);

        // generate an negative number
        Integer expectedScoreAfterDecrement = expectedMovieDTO.getScore() - quantityDTO.getQuantity();

        // then
        assertThrows(MovieScoreExceededException.class, () -> movieService.decrement(expectedMovieDTO.getId(),quantityDTO.getQuantity()));

    }

}
