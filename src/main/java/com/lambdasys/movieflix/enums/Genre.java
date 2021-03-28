package com.lambdasys.movieflix.enums;

public enum Genre {

    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    EPICS("Epics"),
    FANTASY("Fantasy"),
    HISTORICAL("Historical"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SATIRE("Satire"),
    SCIENCE_FICTION("Science Fiction"),
    THRILLER("Thriller"),
    WAR("War"),
    WESTERN("Western");

    Genre(String description) {
        this.description = description;
    }

    private final String description;

    public String getDescription(){
        return this.description;
    }
}
