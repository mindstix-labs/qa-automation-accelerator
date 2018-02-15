Feature: Feature to test searching scenarios

@cbt @ecom @search @web-002 @smoke
Scenario: 
	Given User is browsing 'Automation Practice' website
	When User searches for "Dress"
	Then User lands on "automationpractice.com/index.php?controller=search" Page
	And Verifies that the results contain the word "Dress"