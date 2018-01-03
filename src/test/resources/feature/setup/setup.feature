Feature: Setting up things before other tests execute.

@ecom @setup @sitecheck
Scenario: 
	Given User is browsing 'Automation Practice' website
	When User clicks "Contact us" on "Homepage" 
	Then User lands on "automationpractice.com/index.php?controller=contact" Page

@ecom @setup @usercheck
Scenario Outline:
	Given User is browsing 'Automation Practice' website
	When User clicks "Sign In" on "Homepage" 
	Then User either signs in or registers with email address "<email>" and password "<password>" 
	Examples:
		| email 		| password 		|
		| test1@mx.com 	| mypassword 	|
		| test3@mx.com 	| mypassword 	|
