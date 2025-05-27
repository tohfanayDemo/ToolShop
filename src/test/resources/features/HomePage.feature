#Author: Tohfatul

@Home
Feature: Homepage Functionality

  	Background:
		Given User is on Home Page

####################### BANNER #################################

	@Banner
	Scenario Outline: User clicks on "<Option>" banner option and lands on "<Page>"
	  When User clicks on the "<Option>" banner
	  Then User lands on "<Page>"
	  Examples:
    |Option  |Page       |
    |Home    |HomePage   |
    |Contact |ContactPage|
    |Sign in |LoginPage  |
	
	@Dropdown
	Scenario Outline: User clicks on "<Option>" banner dropdown validation
	  When User clicks on the "<Option>" banner
	  Then User should see a drop down list with <OptionCount> options
	  Examples:
    |Option    |OptionCount|
    |Categories|5          |
    |Language  |6          |


####################### SORTING #################################
    
  @Sort
	Scenario Outline: User sort product based on "<DropdownOption>"
	  When User selects "<DropdownOption>" from the sort dropdown
	  Then User should see products displayed in "<Order>"
	  Examples:
    |DropdownOption    |Order                         |
    |Price (Low - High)|Ascending                     |
    |Price (High - Low)|Descending                    |
    |Name (A - Z)      |Alphabetical order from A to Z|
    |Name (Z - A)      |Alphabetical order from Z to A|  


####################### PRICE FILTER #################################
    
#    @PriceFilter
#    Scenario: User sets both minimum and maximum price using slider
#	  Given User is on Home Page
#	  When User drags the maximum price slider to $150 and minimum price at $50
#	  Then User should see product list priced between $50 and $150

#    Scenario: User sets only a maximum price using slider
#	  Given User is on Home Page
#	  When User drags the maximum price slider to $200 and leave minimum price at $1
#	  Then User should see product list priced at $200 or less

#    Scenario: User drags the minimum price slider to $25 and leave maximum price at $100
#	  Given User is on Home Page
#	  When User drags the maximum price slider to $200 and leave minimum price at $1
#	  Then User should see product list priced between $25 andf $100
      
      
 ####################### SEARCH BOX #################################
   @search
   Scenario: User searches for a product
	  When User enters "plier" in the search box and clicks the search button
	  Then User should see a list of products matching "plier"   

		@search
    Scenario: User enters an invalid product in search
	  When User enters "soap" in the search box and clicks the search button
	  Then User should see messages saying "Searched for: soap" and "There are no products found."
      
      
 ####################### CATEGORY-BASED FILTERING #################################
		
		@category
    Scenario:  User selects a single category filter
	  When User selects the "Screwdriver" category checkbox
	  Then User should see only products in the "Screwdriver" category displayed  

		@category
    Scenario:  User selects multiple categories filter
	  When User selects "Screwdriver" and "Chisels" category checkboxes
	  Then User should see only products in the "Screwdriver" and "Chisels" categories displayed

		@category
   Scenario:  User deselects all category filters
   	Given User selects "Screwdriver" and "Chisels" category checkboxes
	  And User should see only products in the "Screwdriver" and "Chisels" categories displayed
	  When User deselects all the categories
	  Then All products should be displayed again
 
  ####################### BRAND-BASED FILTERING #################################
  
  	@brand
    Scenario:  User selects a single brand filter
	  When User selects the "MightyCraft Hardware" brand checkbox
	  Then User should see only products in the "MightyCraft Hardware" brand category displayed 
		
		@brand
   Scenario: User selects multiple brand  filters
	  When User selects "ForgeFlex Tools" and "MightyCraft Hardware" brand checkboxes
	  Then User should see only products in the "ForgeFlex Tools" and "MightyCraft Hardware" brands displayed

		@brand
   Scenario: User deselects all brand category filters
	  Given User selects "ForgeFlex Tools" and "MightyCraft Hardware" brand checkboxes
	  And User should see only products in the "ForgeFlex Tools" and "MightyCraft Hardware" brands displayed
	  When  User deselects all the brand categories
	  Then All branded products should be displayed again  
 
 
 
