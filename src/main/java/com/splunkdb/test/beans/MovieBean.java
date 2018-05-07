package com.splunkdb.test.beans;

import com.splunkdb.test.domain.MovieResults;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class MovieBean {
    @Getter @Setter
    private MovieResults movieResults;
}
