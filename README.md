# splunkmovieproj

# API Tests for Splunk movie project
Tools used : Cucumber, RestAssured and Maven

Cucumber : Used to define scenarions that are defined as Automation steps in the MovieAPISteps class.

RestAssured : Open source platform used to make API calls in BDD style.

Spring : Used as dependency inject by defining a bean which contains the POJO so any subsequent steps can use the bean instead of making API calls.

Maven : Used for capturing all project dependencies and test execution.

# Bugs found:
 - Somehow I was able to add the same movie name twice.
 - Unable to find a way to add movie image as defined in Requierment - SPL-001
 - After adding a movie, the movie name does not show up in Movie listings.
 - Number of movies with GenreIds greater than 400 was 6 instead of 7.
 - Could not find any movies whose GenreIds were NULL as defined in Requierment - SPL-002

# How to execute

```mvn -U clean install```

# Where to see the reports

```${project.build.dir}/target/cucumber-html-reports/overview-features.html```
