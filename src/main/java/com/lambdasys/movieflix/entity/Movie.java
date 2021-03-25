package com.lambdasys.movieflix.entity;

import com.lambdasys.movieflix.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Movie implements Serializable {

    public static final int YEAR_OF_FIRST_FILM_THE_WORLD = 1888;

    @Id
    private Long id;

    @NotBlank @Size(min = 1 ,max = 255)
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank @Size(min = 10,max = 1000)
    @Column(nullable = false)
    private String description;

    @Min(value = YEAR_OF_FIRST_FILM_THE_WORLD)
    @NotNull
    @Column(nullable = false)
    private Integer year;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private Long views;

    @Column(nullable = false)
    private Long likes;

    @Column(nullable = false)
    private Long dislike;


}
