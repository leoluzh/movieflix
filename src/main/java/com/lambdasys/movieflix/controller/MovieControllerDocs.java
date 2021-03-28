package com.lambdasys.movieflix.controller;

import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.dto.ScoreQuantityDTO;
import com.lambdasys.movieflix.exceptions.MovieAlreadyRegisteredException;
import com.lambdasys.movieflix.exceptions.MovieNotFoundException;
import com.lambdasys.movieflix.exceptions.MovieScoreExceededException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Movie API" , description = "Manage movie catalog")
public interface MovieControllerDocs {

    @Operation(description = "Movie creating operation")
    @ApiResponses(value={
        @ApiResponse(responseCode = "201", description = "Success movie creation.",
                content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = MovieDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Missing required fields or wrong field range value.", content = @Content)
    })
    MovieDTO createMovie(MovieDTO movieDTO) throws MovieAlreadyRegisteredException;

    @Operation(description = "Returns movie found by a given name")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "Success movie found in the system.",
            content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = MovieDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Movie with given name not found.", content = @Content)
    })
    MovieDTO findByName(
            @Parameter(description = "name of movie to be searched") String name)
            throws MovieNotFoundException;

    @Operation(description = "Return a list of all movies registered in the system.")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200" , description = "List of all movies registered in the system."),
    })
    List<MovieDTO> listMovies();

    @Operation(description="Delete an movie found by a given valid id")
    @ApiResponses(value={
        @ApiResponse(responseCode = "204", description = "Success movie deleted in the system." , content = @Content(mediaType = "application/json")) ,
        @ApiResponse(responseCode = "404", description = "Movie with given id not found.", content = @Content)
    })
    void deleteById(
            @Parameter(description = "id of movie to be deleted") Long id)
            throws MovieNotFoundException;

    @Operation(description = "Increment an movie views by a given id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Success liked movie in the system.",
            content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = MovieDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Movie with given id not found.", content = @Content)
    })
    MovieDTO views(
            @Parameter(description = "id of movie to be increment number of views") Long id)
            throws MovieNotFoundException;


    @Operation(description = "Like an movie by a given id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Success liked movie in the system.",
            content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = MovieDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Movie with given id not found.", content = @Content)
    })
    MovieDTO like(
            @Parameter(description = "id of movie to be incremented the number of likes")  Long id)
            throws MovieNotFoundException;

    @Operation(description = "Dislike an movie by a given id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Success disliked movie in the system.",
            content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = MovieDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Movie with given id not found.", content = @Content)
    })
    MovieDTO dislike(
            @Parameter(description = "id of movie to be incremented the number of dislikes") Long id)
            throws MovieNotFoundException;

    @Operation(description = "Increment movie's score by a given id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Success movie's score incremented",
            content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = MovieDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Movie's score not successfully increment", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie with given id not found.", content = @Content)
    })
    MovieDTO increment(
            @Parameter(description = "id of movie to be incremented the number of score") Long id,
            @Parameter(description = "the quantity/amount to be increment in the number of score of a given movie") ScoreQuantityDTO scoreQuantityDTO)
            throws MovieNotFoundException, MovieScoreExceededException;

    @Operation(description = "Decrement movie's score by a given id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Success movie's score decremented",
            content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = MovieDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Movie's score not successfully decremented", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie with given id not found.", content = @Content)
    })
    MovieDTO decrement(
            @Parameter(description = "id of movie to be decremented the number of score") Long id,
            @Parameter(description = "the quantity/amount to be decrement in the number of score of a given movie") ScoreQuantityDTO scoreQuantityDTO) throws MovieNotFoundException, MovieScoreExceededException;

}
