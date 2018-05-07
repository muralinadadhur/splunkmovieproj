package com.splunkdb.test.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieResults {
    private List<MovieResultDetails> results;
}
