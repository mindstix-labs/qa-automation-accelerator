Feature: Test Weather API.

Background:
	Given API baseUrl "http://restapi.demoqa.com/utilities/weather/city/"

@api @cbt
Scenario Outline:
	Given City of "<city>"
    And invoke weather API
    Then verify the response code is "<code>" and body contains city name "<verifyCity>"
    Examples:
		| city 		| code | verifyCity 	|
		| pune 	    | 200 | Pune 	        |
		| hyderabad | 200 | Hyderabad 	|
		| invalid | 400 ||
