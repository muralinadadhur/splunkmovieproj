package com.splunkdb.test.domain;

import java.util.List;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class MovieResultDetails {

    private List<MovieResultDetails> results;
    private Integer vote_count;
    private String id;
    private String video;
    private Double vote_average;
    private String title;
    private Double popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private List<Integer> genre_ids;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String release_date;
}
