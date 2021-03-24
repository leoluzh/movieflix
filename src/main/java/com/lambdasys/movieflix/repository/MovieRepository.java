package com.lambdasys.movieflix.repository;

import com.lambdasys.movieflix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    Optional<Movie> findByName(String name );

}
