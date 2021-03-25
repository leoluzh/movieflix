package com.lambdasys.movieflix.controller;


import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.exceptions.MovieAlreadyRegisteredException;
import com.lambdasys.movieflix.exceptions.MovieNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Movie API" , description = "Manage movie catalog")
public interface MovieControllerDocs {

    @Operation(description = "Movie creating operation")
    @ApiResponses(value={
        @ApiResponse(responseCode = "201", description = "Success movie creation."),
        @ApiResponse(responseCode = "400", description = "Missing required fields or wrong field range value.")
    })
    MovieDTO createMovie( MovieDTO movieDTO ) throws MovieAlreadyRegisteredException;

    @Operation(description = "Returns movie found by a given name")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "Success movie found in the system."),
        @ApiResponse(responseCode = "404", description = "Movie with given name not found.")
    })
    MovieDTO findByName(@PathVariable String name ) throws MovieNotFoundException;

    @Operation(description = "Return a list of all movies registered in the system.")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200" , description = "List of all movies registered in the system."),
    })
    List<MovieDTO> listMovies();

    @Operation(description="Delete an movie found by a given valid id")
    @ApiResponses(value={
        @ApiResponse(responseCode = "204", description = "Success movie deleted in the system.") ,
        @ApiResponse(responseCode = "404", description = "Movie with given id not found.")
    })
    void deleteById( @PathVariable Long id ) throws MovieNotFoundException;

    @Operation(description = "Increment an movie views by a given id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Success liked movie in the system."),
    })
    MovieDTO views( @PathVariable Long id );


    @Operation(description = "Like an movie by a given id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Success liked movie in the system."),
    })
    MovieDTO like( @PathVariable Long id );

    @Operation(description = "Dislike an movie by a given id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Success disliked movie in the system."),
    })
    MovieDTO dislike( @PathVariable Long id );

}
