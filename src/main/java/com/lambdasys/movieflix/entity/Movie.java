package com.lambdasys.movieflix.entity;

import com.lambdasys.movieflix.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@SuppressWarnings(value="serial")
public class Movie implements Serializable {

    public static final int YEAR_OF_FIRST_FILM_THE_WORLD = 1888;

    @Id @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private Long views = 0L;

    @Column(nullable = false)
    private Long likes = 0L;

    @Column(nullable = false)
    private Long dislikes = 0L;

    @Column(nullable = false)
    private Integer max = 0;

    @Column(nullable = false)
    private Integer score = 0;

}
