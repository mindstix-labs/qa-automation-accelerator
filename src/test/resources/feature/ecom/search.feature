Feature: Feature to test searching scenarios

@cbt @ecom @search @web-002 @smoke
Scenario: search
	Given User is browsing 'Automation Practice' website
	When User searches for "Dress"
	Then User lands on "automationpractice.com/index.php?controller=search" Page
	And Verifies that the results contain the word "Dress"
	
@web @cbt @ecom @signin @web-001 @smoke
Scenario Outline: signin
	Given User is browsing 'Automation Practice' website
	When User clicks "Sign In" on "Homepage" 
	And User lands on "automationpractice.com/index.php?controller=authentication&back=my-account" Page
	And inputs email address "<email>" and password "<password>"
	Then User lands on "automationpractice.com/index.php?controller=my-account" Page
	Examples:
		| email 		| password 		|
		| test1@mx.com 	| mypassword 	|
