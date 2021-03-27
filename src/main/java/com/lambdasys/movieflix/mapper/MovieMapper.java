package com.lambdasys.movieflix.mapper;

import com.lambdasys.movieflix.dto.MovieDTO;
import com.lambdasys.movieflix.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    Movie toModel(MovieDTO movieDTO);

    MovieDTO toDTO(Movie movie);

}
