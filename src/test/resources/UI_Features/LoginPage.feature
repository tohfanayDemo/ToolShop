#Author: Tohfa
@login
Feature: Login Functionality
  
  	Background:
		Given User is on Home Page
		And User clicks on the "Sign in" banner
		
#		@positive
#		Scenario: Validate login functionality with valid data in all fields
    #When User enter valid data in all field and clicks login button 
    #Then User should land on home page 
#	
		@testData
   Scenario Outline: Validate login functionality with invalid data - "<Testcase>"
    When User enters value for "<Testcase>" in email and password field
    Then User should see proper error message
    
    Examples:
    |Testcase                       |
    |Password_Incorrect             |
    |Password_IncorrectCase_AllLower|
    |Password_IncorrectCase_AllUpper|
    |Credentials_Incorrect          |
    |Username_Unregistered          |
    |Username_InvalidFormat_Without@|
    
    
    
  Scenario: Validate login functionality with null user name
    When User enters value only in password and clicks login button
    Then User should see error message "Password is required"
   
  Scenario: Validate login functionality with null password
    When User enters value only in email address and clicks login button
    Then User should see error message "Email is required"  
  
  Scenario: Validate login functionality with null credentials
    When User enters no values in email address and and password and clicks login button
    Then User should see both error messages "Email is required" and "Password is required"  

	Scenario: Validate Registration link navigates user to Registration page
    When User clicks on the "Register your account" link
    Then User is navigated to Registration Page  
