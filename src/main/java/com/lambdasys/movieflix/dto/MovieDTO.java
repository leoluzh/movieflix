package com.lambdasys.movieflix.dto;

import com.lambdasys.movieflix.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO implements Serializable {

    public static final int YEAR_OF_FIRST_FILM_THE_WORLD = 1888;

    private Long id;

    @NotBlank @Size(min=1,max=255)
    private String name;

    @NotBlank @Size(min=10,max=1000)
    private String description;

    @NotNull @Min(value = YEAR_OF_FIRST_FILM_THE_WORLD)
    private Integer year;

    @NotNull
    private Genre genre;

    @NotNull @Min(0) @Max(999_999_999_999L)
    private Long views;

    @NotNull @Min(0) @Max(999_999_999_999L)
    private Long likes;

    @NotNull @Min(0) @Max(999_999_999_999L)
    private Long dislikes;

    @NotNull @Min(0) @Max(1000)
    private Integer max;

    @NotNull @Min(0) @Max(1000)
    private Integer score;

}
