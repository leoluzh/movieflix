package com.lambdasys.movieflix.builder;

import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.entity.Movie;
import com.lambdasys.movieflix.enums.Genre;
import com.lambdasys.movieflix.mapper.MovieMapper;
import lombok.Builder;

@Builder
public class MovieDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Matrix";

    @Builder.Default
    private String description =
    """
    Um jovem programador é atormentado por estranhos pesadelos nos quais sempre está conectado por cabos a um imenso 
    sistema de computadores do futuro. À medida que o sonho se repete, ele começa a levantar dúvidas sobre a realidade. 
    E quando encontra os misteriosos Morpheus e Trinity, ele descobre que é vítima do Matrix, um sistema inteligente e 
    artificial que manipula a mente das pessoas e cria a ilusão de um mundo real enquanto usa os cérebros e corpos dos 
    indivíduos para produzir energia.            
    """;

    @Builder.Default
    private Integer year = 1999;

    @Builder.Default
    private Genre genre = Genre.SCIENCE_FICTION;

    @Builder.Default
    private Long views = 1_000_000_000L;

    @Builder.Default
    private Long likes = 1_000_000L;

    @Builder.Default
    private Long dislikes = 100_000L;

    public Movie toMovie(){
        return Movie
                .builder()
                .id( id )
                .name( name )
                .description( description )
                .year( year )
                .genre( genre )
                .views( views )
                .likes( likes )
                .dislikes( dislikes )
                .build();
    }

    public MovieDTO toMovieDTO(){
         return MovieMapper.INSTANCE.toDTO( toMovie() );
    }

}
