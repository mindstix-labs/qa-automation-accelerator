Feature: Feature to test seaching scenarios

@cbt @ecom @search
Scenario: 
	Given User is browsing 'Automation Practice' website
	When User searches for "Dress"
	Then User lands on "automationpractice.com/index.php?controller=search" Page
	And verifies all result product contain the word "Dress"
