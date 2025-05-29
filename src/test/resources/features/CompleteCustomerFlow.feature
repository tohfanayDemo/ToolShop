@Author_Tohfatul
@endToEnd
Feature: Complete Customer Flow

	@smoke
	Scenario: New customer registers, places an order, and views order history

		Given User is on Home Page
		And User clicks on the "Sign in" banner
		And User clicks on the "Register your account" link
   
   	And User clicks on Register button with valid data
 		And User is navigated to Login Page
 		
    And User enters valid data in all field and clicks login button 
    And User should land on MyAccount page
    
    And User clicks the "Home" banner from MyAccount Page 
    And User is on Home Page
    
    And User selects the "ForgeFlex Tools" brand checkbox
    And User selects the "Pliers" category checkbox
    And User clicks "Combination Pliers" image and views product details
    
    And User enters quantity and adds the product to the cart 
 		Then User sees product details in CartPage
    And completes the checkout process
