#Author: Tohfatul
@registration
Feature: Registration Functionality

Background:
		Given User is on Home Page
		And User clicks on the "Sign in" banner
		And User clicks on the "Register your account" link
		
	Scenario: User submits empty form
		When User clicks on Register button without filling up form
		Then User sees error msg for each field
	
#	Scenario: User registers with valid data
#		When User clicks on Register button with valid data
#		Then User is navigated to Login Page	
		
	@testData
	Scenario Outline: Password fulfilling "<Testcase>" shows "<Level>" password strength
	  When User enters value for "<Testcase>" in password field
	  Then User should see password strength bar and label to be "<Level>"
	  Examples:
    |Testcase                                            |Level      |
    |1Condition_Lowercase                                |Weak       |
    |2Conditions_Lower&Uppercase                         |Moderate   |
    |3Conditions_Lower&Uppercase&Number                  |Strong     |
    |4Conditions_Lower&Uppercase&Number&SpecialChar      |Very Strong|
    |5Conditions_Lower&Uppercase&Number&SpecialChar&8Char|Excellent  |
    
  @testData @wip
	Scenario Outline: Validate Registration functionality for scenario "<Testcase>" with invalid data
	  When User enters for data in form for scenario "<Testcase>"
	  Then User should see error message
	  Examples:
    |Testcase        |               
    |FirstName_Empty |          
    |LastName_Empty  |   
    |DOB_Empty       |
    |Street_Empty    |
    |PostalCode_Empty|
    |City_Empty      |
    |State_Empty     |
    |Country_Empty   |
    |Phone_Empty     |
    |Email_Empty     |
    #|Password_Empty  |

   
    