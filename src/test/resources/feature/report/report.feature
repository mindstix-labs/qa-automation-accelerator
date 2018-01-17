Feature: Email Report 

@mailReport @report 
Scenario Outline: 
	Given Setup report header 
	When Setup report body 
	When Setup report footer 
	Then Email report "<to>"
	
	Examples: 
		| to |
		| manoj.sathe@mindstix.com |
	