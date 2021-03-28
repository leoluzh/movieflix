package com.lambdasys.movieflix.controller;

import com.lambdasys.movieflix.builder.MovieDTOBuilder;
import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.dto.ScoreQuantityDTO;
import com.lambdasys.movieflix.exceptions.MovieNotFoundException;
import com.lambdasys.movieflix.exceptions.MovieScoreExceededException;
import com.lambdasys.movieflix.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.lambdasys.movieflix.utils.JsonConvertionUtils.asJsonString;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    private static final String MOVIE_API_URL_PATH = "/api/v1/movies" ;
    private static final Long VALID_MOVIE_ID = 1L;
    private static final Long INVALID_MOVIE_ID = 2L;
    private static final String MOVIE_API_SUBPATH_LIKES_URL = "/like" ;
    private static final String MOVIE_API_SUBPATH_DISLKES_URL = "/dislike" ;
    private static final String MOVIE_API_SUBPATH_VIEWS_URL = "/view" ;
    private static final String MOVIE_API_SUBPATH_SCORE_INCREMENT_URL = "/increment" ;
    private static final String MOVIE_API_SUBPATH_SCORE_DECREMENT_URL = "/decrement" ;

    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup( movieController )
                .setCustomArgumentResolvers( new PageableHandlerMethodArgumentResolver() )
                .setViewResolvers( (s, locale) ->  new MappingJackson2JsonView() )
                .build();
    }

    @Order(1)
    @DisplayName("When POST is called then an movie is created")
    @Test
    public void whenPOSTIsCalledThenAnMovieIsCreated() throws Exception {

        // given
        MovieDTO movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieService.createMovie(movieDTO)).thenReturn(movieDTO);

        // then
        mockMvc.perform(post(MOVIE_API_URL_PATH)
            .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(asJsonString(movieDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",is(movieDTO.getName())))
                .andExpect(jsonPath("$.description",is(movieDTO.getDescription())))
                .andExpect(jsonPath("$.year",is(movieDTO.getYear())))
                .andExpect(jsonPath("$.genre",is(movieDTO.getGenre().toString())))
                .andExpect(jsonPath("$.views",is(equalTo(movieDTO.getViews())),Long.class))
                .andExpect(jsonPath("$.likes",is(equalTo(movieDTO.getLikes())),Long.class))
                .andExpect(jsonPath("$.dislikes",is(equalTo(movieDTO.getDislikes())),Long.class));
    }

    @Order(2)
    @DisplayName("When POST is called without required field then an error is returned")
    @Test
    public void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {

        // given
        MovieDTO movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        movieDTO.setName(null);

        // then
        mockMvc.perform(post(MOVIE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(asJsonString(movieDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Order(3)
    @DisplayName("When GET is called with valid name then ok status is returned")
    @Test
    public void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {

        // given
        MovieDTO movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieService.findByName(movieDTO.getName())).thenReturn(movieDTO);

        // then
        var path = MOVIE_API_URL_PATH + "/" + movieDTO.getName();
        mockMvc.perform(get(path)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(movieDTO.getName())))
                .andExpect(jsonPath("$.description",is(movieDTO.getDescription())))
                .andExpect(jsonPath("$.year",is(movieDTO.getYear())))
                .andExpect(jsonPath("$.genre",is(movieDTO.getGenre().toString())))
                .andExpect(jsonPath("$.views",is(equalTo(movieDTO.getViews())),Long.class))
                .andExpect(jsonPath("$.likes",is(equalTo(movieDTO.getLikes())),Long.class))
                .andExpect(jsonPath("$.dislikes",is(equalTo(movieDTO.getDislikes())),Long.class));

    }

    @Order(4)
    @DisplayName("When GET is called without registered name then not found status is returned")
    @Test
    public void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {

        // given
        MovieDTO movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieService.findByName(movieDTO.getName())).thenThrow(MovieNotFoundException.class);

        // then
        var path = MOVIE_API_URL_PATH + "/" + movieDTO.getName() ;
        mockMvc.perform(get(path)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Order(5)
    @DisplayName("When GET list with movies is called then ok status is returned")
    @Test
    public void whenGETListWithMoviesIsCalledThenOkStatusIsReturned() throws Exception {

        // given
        MovieDTO movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieService.listAll()).thenReturn(Collections.singletonList(movieDTO));

        // then
        mockMvc.perform(get(MOVIE_API_URL_PATH)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",is(movieDTO.getName())))
                .andExpect(jsonPath("$[0].description",is(movieDTO.getDescription())))
                .andExpect(jsonPath("$[0].year",is(movieDTO.getYear())))
                .andExpect(jsonPath("$[0].genre",is(movieDTO.getGenre().toString())))
                .andExpect(jsonPath("$[0].views",is(equalTo(movieDTO.getViews())),Long.class))
                .andExpect(jsonPath("$[0].likes",is(equalTo(movieDTO.getLikes())),Long.class))
                .andExpect(jsonPath("$[0].dislikes",is(equalTo(movieDTO.getDislikes())),Long.class));

    }

    @Order(6)
    @DisplayName("When GET list without movies is called then ok status is returned")
    @Test
    public void whenGETListWithoutMoviesIsCalledThenOkStatusIsReturned() throws Exception {

        // given
        MovieDTO movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieService.listAll()).thenReturn(Collections.singletonList(movieDTO));

        // then
        mockMvc.perform(get(MOVIE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isOk());

    }

    @Order(7)
    @DisplayName("When DELETE is called with valid id then no content status is returned")
    @Test
    public void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {

        // given
        MovieDTO movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        doNothing().when(movieService).deleteById(movieDTO.getId());

        // then
        var path = MOVIE_API_URL_PATH + "/" + movieDTO.getId() ;
        mockMvc.perform(delete(path)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isNoContent());

    }

    @Order(8)
    @DisplayName("When DELETE is called with invalid id then not found status is returned")
    @Test
    public void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {

        // when
        doThrow(MovieNotFoundException.class).when(movieService).deleteById(INVALID_MOVIE_ID);

        // then
        final var path = MOVIE_API_URL_PATH + "/" + INVALID_MOVIE_ID;
        System.out.println(String.format("delete path: %s",path));

        mockMvc.perform(delete(path)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isNotFound());

    }

    @Order(9)
    @DisplayName("When PATCH is called to increment score greather than max then bad request status is returned")
    @Test
    public void whenPATCHIsCalledToIncrementScoreGreatherThanMaxThenBadRequestStatusIsReturned() throws Exception {

        // given
        var quantityDTO = ScoreQuantityDTO.builder().quantity(500).build();
        var movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieService.increment(VALID_MOVIE_ID,quantityDTO.getQuantity())).thenThrow(MovieScoreExceededException.class);

        // then
        var path = MOVIE_API_URL_PATH + "/" + VALID_MOVIE_ID + MOVIE_API_SUBPATH_SCORE_INCREMENT_URL;
        mockMvc.perform(patch(path)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(asJsonString(quantityDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Order(10)
    @DisplayName("When PATCH is called with invalid movie id to increment score then not found status is returned")
    @Test
    public void whenPATCHIsCalledWithInvalidMovieIdToIncrementScoreThenNotFoundStatusIsReturned() throws Exception {

        // given
        var quantityDTO = ScoreQuantityDTO.builder().quantity(1000).build();
        var movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieService.increment(VALID_MOVIE_ID,quantityDTO.getQuantity())).thenThrow(MovieNotFoundException.class);

        // then
        var path = MOVIE_API_URL_PATH + "/" + VALID_MOVIE_ID + MOVIE_API_SUBPATH_SCORE_INCREMENT_URL;
        mockMvc.perform(patch(path)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(asJsonString(quantityDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Order(11)
    @DisplayName("When PATH is called to decrement score then ok stauts is returned")
    @Test
    public void whenPATCHIsCalledToDecrementScoreThenOkStatusIsReturned() throws Exception {

        // given
        var quantityDTO = ScoreQuantityDTO.builder().quantity(5).build();
        var movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();

        // when
        when(movieService.decrement(VALID_MOVIE_ID,quantityDTO.getQuantity())).thenReturn(movieDTO);

        // then
        var path = MOVIE_API_URL_PATH + "/" + VALID_MOVIE_ID + MOVIE_API_SUBPATH_SCORE_DECREMENT_URL;
        mockMvc.perform(patch(path)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(asJsonString(quantityDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(movieDTO.getName())))
                .andExpect(jsonPath("$.description",is(movieDTO.getDescription())))
                .andExpect(jsonPath("$.year",is(movieDTO.getYear())))
                .andExpect(jsonPath("$.genre",is(movieDTO.getGenre().toString())))
                .andExpect(jsonPath("$.views",is(equalTo(movieDTO.getViews())),Long.class))
                .andExpect(jsonPath("$.likes",is(equalTo(movieDTO.getLikes())),Long.class))
                .andExpect(jsonPath("$.dislikes",is(equalTo(movieDTO.getDislikes())),Long.class))
                .andExpect(jsonPath("$.score",is(equalTo(movieDTO.getScore()))))
                .andExpect(jsonPath("$.max",is(equalTo(movieDTO.getMax()))));

    }

    @Order(12)
    @DisplayName("When PATCH is called to decrement lower than zero then bad request status is returned")
    @Test
    public void whenPATCHIsCalledToDecrementLowerThanZeroThenBadRequestStatusIsReturned() throws Exception {

        // given
        var quantityDTO = ScoreQuantityDTO.builder().quantity(50).build();
        var movieDTO = MovieDTOBuilder.builder().build().toMovieDTO();
        movieDTO.setScore(movieDTO.getScore()-quantityDTO.getQuantity());

        // when
        when(movieService.decrement(VALID_MOVIE_ID,quantityDTO.getQuantity())).thenThrow(MovieScoreExceededException.class);


        // then
        var path = MOVIE_API_URL_PATH + "/" + VALID_MOVIE_ID + MOVIE_API_SUBPATH_SCORE_DECREMENT_URL;
        mockMvc.perform(patch(path)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(asJsonString(quantityDTO)))
                .andExpect(status().isBadRequest());

    }

    @Order(13)
    @DisplayName("When PATCH is called with invalid movie id to decrement then not found status is returned")
    @Test
    public void whenPATCHIsCalledWithInvalidMovieIdToDecrementThenNotFoundStatusIsReturned() throws Exception {

        // given
        var quantityDTO = ScoreQuantityDTO.builder().quantity(5).build();

        // when
        when(movieService.decrement(INVALID_MOVIE_ID,quantityDTO.getQuantity())).thenThrow(MovieNotFoundException.class);

        // then
        var path = MOVIE_API_URL_PATH + "/" + INVALID_MOVIE_ID + MOVIE_API_SUBPATH_SCORE_DECREMENT_URL;
        mockMvc.perform(patch(path)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(asJsonString(quantityDTO)))
                .andExpect(status().isNotFound());

    }

}
