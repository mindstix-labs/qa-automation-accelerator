Feature: Feature to test sign in scenarios

@cbt @ecom @signin
Scenario Outline:
	Given User is browsing 'Automation Practice' website
	When User clicks "Sign In" on "Homepage" 
	And User lands on "automationpractice.com/index.php?controller=authentication&back=my-account" Page
	And inputs email address "<email>" and password "<password>"
	Then User lands on "automationpractice.com/index.php?controller=my-account" Page
	Examples:
		| email 		| password 		|
		| test1@mx.com 	| mypassword 	|
		# This is intended to fail as there is no such user
		| test2@mx.com 	| invalidpassword 	|
