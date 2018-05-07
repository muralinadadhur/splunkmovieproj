package com.splunkdb.test.actions;


import com.splunkdb.test.domain.MovieResults;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import com.splunkdb.test.domain.JsonCreateMovie;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.isOneOf;



public class MovieAPIActions  extends AbstractAPIActions {

    public MovieResults getMovieInfo() {
        ExtractableResponse<Response> spec =
                given()
                   .header("Accept","application/json")
                .expect()
                    .statusCode(isOneOf(SC_OK))
                    .request()
                .when()
                    .log().method()
                    .log().uri()
                    .queryParam("q","batman")
                .get("https://splunk.mocklab.io/movies")
                    .then().log().all()
                .extract();

        responseInfo.setStatusCode(spec.statusCode());
        return spec.response().as(MovieResults.class);
    }

    public String postMovieInfo(JsonCreateMovie jsonCreateMovie) {
        ExtractableResponse<Response> spec =
                given()
                        .header("Content-Type","application/json")
                        .expect()
                        .statusCode(isOneOf(SC_OK))
                        .request()
                        .when()
                        .body(jsonCreateMovie)
                        .post("https://splunk.mocklab.io/movies")
                        .then().log().all()
                        .extract();
        responseInfo.setStatusCode(spec.statusCode());
        String responseString = spec.response().getBody().asString();
        return  responseString;
    }
}
