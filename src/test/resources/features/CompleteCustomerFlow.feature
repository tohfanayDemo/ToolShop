#Author: Tohfatul

Feature: Complete Customer Flow

	@CompleteCustomer
	Scenario: New customer registers, places an order, and views order history

		Given User is on Home Page
		And User clicks on the "Sign in" banner
		And User clicks on the "Register your account" link
   
   	When User clicks on Register button with valid data
 		Then User is navigated to Login Page
 		
    When User enters valid data in all field and clicks login button 
    Then User should land on MyAccount page
    When User clicks the "Home" banner from MyAccount Page 
    And User is on Home Page
    And User selects the "ForgeFlex Tools" brand checkbox
    And User selects the "Pliers" category checkbox
    And User clicks "Combination Pliers" image and views product details
    
    Then User enters quantity and adds the product to the cart 
 		And User sees product details in CartPage
    And completes the checkout process

