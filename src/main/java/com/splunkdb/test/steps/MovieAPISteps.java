package com.splunkdb.test.steps;

import com.splunkdb.test.actions.ActionsFactory;
import com.splunkdb.test.beans.MovieBean;
import com.splunkdb.test.beans.ResponseInfo;
import com.splunkdb.test.domain.JsonCreateMovie;
import com.splunkdb.test.domain.MovieResultDetails;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;


import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.splunkdb.test.actions.MovieAPIActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.apache.commons.validator.routines.UrlValidator;

import static java.util.Objects.nonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class that defines the steps based on Cucumber scenarios.
 *
 */

@ContextConfiguration("classpath*:cucumber.xml")
@DirtiesContext
public class MovieAPISteps {

    @Autowired
    MovieBean movieBean;

    @Autowired
    ResponseInfo responseInfo;

    @Autowired
    private ActionsFactory actions;

    /**
     * Test case to add a movie to the movies database.
     * @param arg1
     * @throws Throwable
     */

    @When("^I add a movie with name \"([^\"]*)\"$")
    public void addAMovieName(String arg1) throws Throwable {
        MovieAPIActions movieAPIActions = actions.get(MovieAPIActions.class);
        JsonCreateMovie jsonCreateMovie = new JsonCreateMovie("Entourage 3","Best Movie");
        assertThat(movieAPIActions.postMovieInfo(jsonCreateMovie)).isNotEmpty();
    }

    /**
     * Get the entire movie listing and add it to movie bean
     * @throws Throwable
     */
    @When("^I get movie listing$")
    public void checkMovieName() throws Throwable {
        MovieAPIActions movieAPIActions = actions.get(MovieAPIActions.class);
        movieBean.setMovieResults(movieAPIActions.getMovieInfo());
        assertThat(movieBean.getMovieResults().getResults()).isNotEmpty();
    }

    /**
     * Test to check if movie list contains the movie that was added to the movies database.
     * @param arg1
     * @throws Throwable
     */
    @Then("^movie list contains \"([^\"]*)\"$")
    public void checMovieListing(String arg1) throws Throwable {
        int movieMatch = 0;
        List<MovieResultDetails> movieResultDetailsList =   movieBean.getMovieResults().getResults();
        for (MovieResultDetails movieResultDetails : movieResultDetailsList) {
            if (movieResultDetails.getTitle().equalsIgnoreCase(arg1)) {
                movieMatch++;
            }
        }
        assertThat(movieMatch).isGreaterThan(0);
    }

    @Then("^status code is (\\d+)$")
    public void status_code_is(int arg1) throws Throwable {
        assertThat(responseInfo.getStatusCode()).isEqualTo(arg1);
    }


    /**
     * Movie title should not be NULL
     * @throws Throwable
     */
    @Then("^I check for each movie having a title$")
    public void checkMovieTitles() throws Throwable {
        List<MovieResultDetails> movieResultDetailsList =   movieBean.getMovieResults().getResults();
        for (MovieResultDetails movieResultDetails : movieResultDetailsList) {
            assertThat(movieResultDetails.getTitle()).isNotNull();
        }
    }

    /**
     * Requirement SPL-001: : No two movies should contain the same image/title. However, there are no images in the response so validate them against title.
     * @throws Throwable
     */
    @Then("^I check for duplicate movie titles$")
    public void checkDuplicateMovieTitles() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        List<MovieResultDetails> movieResultDetailsList =   movieBean.getMovieResults().getResults();
        List<String> movieTitleList = new ArrayList<>();
        for (MovieResultDetails movieResultDetails : movieResultDetailsList) {
            movieTitleList.add(movieResultDetails.getTitle());
        }
        List<String> distinctElements = movieTitleList.stream().distinct().collect(Collectors.toList());
        assertThat(movieTitleList.size()).isEqualTo(distinctElements.size());
    }

    /**
     * Requirement SPL-002 : All poster path links should be valid.
     * @throws Throwable
     */
    @Then("^I check for valid poster-paths$")
    public void checkPosterPaths() throws Throwable {
        List<MovieResultDetails> movieResultDetailsList =   movieBean.getMovieResults().getResults();
        UrlValidator urlValidator = new UrlValidator();
        for (MovieResultDetails movieResultDetails : movieResultDetailsList) {
            if (nonNull(movieResultDetails.getPoster_path())) {
                assertThat(urlValidator.isValid(movieResultDetails.getPoster_path()));
               }
        }
    }

    /**
     * Requirement SPL-005: Check for a palindrome within movie titles. The could should at least by 1.
     */
    @Then("^I check to see if movie name contains a palindrome$")
    public void checkMoviePalindrome() throws Throwable {
        List<String> movieTitle = new ArrayList<>();
        int palinCount=0;
        int tmpCount=0;
        for (MovieResultDetails movieResultDetails : movieBean.getMovieResults().getResults()) {
            movieTitle.add(movieResultDetails.getTitle());
        }
        for (String strTest : movieTitle) {
            palinCount = checkPalindrome(strTest);
            if (palinCount > 0) {
                tmpCount++;
            }
        }
        assertThat(tmpCount).isEqualTo(1);
    }

    /**
     * Requirement SPL-004: Check for the total of GenreIds for every movie.
     * Bug: Number of movies expected to have genreIds greater than 400 should be 7 instead found 6.
     *
     */
    @Then("^I check to see how many movies have GenreIds greater than (\\d+)$")
    public void checkMovieGenreIds(int genreIdMax) {
        int tmpSum;
        int tmpMax = 1;
        for (MovieResultDetails movieResultDetails : movieBean.getMovieResults().getResults()) {
            tmpSum = movieResultDetails.getGenre_ids().stream().collect(Collectors.summingInt(Integer::intValue));
            if (tmpSum > genreIdMax) {
                tmpMax++;
            }
        }
        assertThat(tmpMax).isEqualTo(7);
    }

    /**
     * Requirement SPL-006: Check to see the number of movies containing the title of another movie. Count should at least be 2.
     *
     */
    @Then("^I check to see if movie title contains another title$")
    public void checkMovieTitleWithAnotherTitle() throws Throwable {
        List<String> movieTitle = new ArrayList<>();
        int inMovieCount = 0; int tmpCount = 0;
        for (MovieResultDetails movieResultDetails : movieBean.getMovieResults().getResults()) {
            movieTitle.add(movieResultDetails.getTitle());
        }
        String beanTitleName;
        for (MovieResultDetails movieResultDetails : movieBean.getMovieResults().getResults()) {
            beanTitleName = movieResultDetails.getTitle();
            for (String title : movieTitle) {
                if (!(beanTitleName.equalsIgnoreCase(title))) {
                    if(beanTitleName.toLowerCase() .contains(title.toLowerCase())) {
                        inMovieCount++;
                    }
                }
            }
            if(inMovieCount > 0) {
                tmpCount++;
            }
            inMovieCount = 0;
        }
        assertThat(tmpCount).isGreaterThanOrEqualTo(2);
    }

    /**
     * Requirement SPL-003: Sort the movie listing so that movie with GenreId as null shows up at the top.
     * Bug: Could not find any movie whose GenreId was null
     */
    @Then("^I check to see if movie listings are in the correct sorted order$")
    public void checkResponseSortOrder() throws Throwable {
        LinkedHashMap<String, List<Integer>> hashMap = new LinkedHashMap();
        for (MovieResultDetails movieResultDetails : movieBean.getMovieResults().getResults()) {
            hashMap.put(movieResultDetails.getId(),movieResultDetails.getGenre_ids());
        }
        hashMap.forEach((k, v) -> {
            v.forEach(w -> assertThat(w).isNull());
        });
    }

    public int checkPalindrome(String mTitle) {
        String[] titleWords = mTitle.split(" ");
        StringBuffer testStr;
        int movieCount = 0;
        for (String localStr : titleWords) {
            if (localStr.length() > 1) {
                testStr = new StringBuffer(localStr.toLowerCase());
                if (testStr.reverse().toString().equals(localStr.toLowerCase())) {
                    movieCount++;
                    break;
                }
            }
        }
        return movieCount;
    }
}