Feature: Movie Listings

Scenario: Verify Movie API allows user to add a movie
  When I add a movie with name "Entourage"
  Then I get movie listing
  And status code is 200
  And movie list contains "Entourage"

Scenario: Verify Movie API returns all movie listing
  When I get movie listing
  And status code is 200

Scenario: Verify Movie names are not repeated
  When I get movie listing
  And status code is 200
  Then I check for each movie having a title
  Then I check for duplicate movie titles

Scenario: Verify Movie poster-path links have valid URLs
   When I get movie listing
   And status code is 200
   And I check for valid poster-paths

 Scenario: Verify sort order of movie listings
   When I get movie listing
   And status code is 200
   And I check to see if movie listings are in the correct sorted order

Scenario: Verify Movie GenreIds greater than 400
   When I get movie listing
   And status code is 200
   And I check to see how many movies have GenreIds greater than 400

Scenario: Verify movie name contains a palindrome
   When I get movie listing
   And status code is 200
   And I check to see if movie name contains a palindrome

 Scenario: Verify movie title contains another title
   When I get movie listing
   And status code is 200
   And I check to see if movie title contains another title