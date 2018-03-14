Feature: Feature to test sign in scenarios

@web @cbt @ecom @signin @web-001 @smoke
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
		
@web @cbt @ecom @signin @web-002 @smoke @signingusingredis
Scenario:
	Given User is browsing 'Automation Practice' website
	When User clicks "Sign In" on "Homepage" 
	And User lands on "automationpractice.com/index.php?controller=authentication&back=my-account" Page
	And inputs email address and password from redis set
	Then User lands on "automationpractice.com/index.php?controller=my-account" Page
	When User searches for "Dress"
	Then User lands on "automationpractice.com/index.php?controller=search" Page
	And Verifies that the results contain the word "Dress"
	
		
@web @cbt @ecom @signin @web-003 @smoke @signingusingredis
Scenario:
	Given User is browsing 'Automation Practice' website
	When User clicks "Sign In" on "Homepage" 
	And User lands on "automationpractice.com/index.php?controller=authentication&back=my-account" Page
	And inputs email address and password from redis set
	Then User lands on "automationpractice.com/index.php?controller=my-account" Page
	When User searches for "Shirt"
	Then User lands on "automationpractice.com/index.php?controller=search" Page
	And Verifies that the results contain the word "Shirt"
				