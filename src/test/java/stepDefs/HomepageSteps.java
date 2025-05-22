package stepDefs;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import constants.UIConstants;
import driver.WebDriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Register;
import pages.CheckoutPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utils.Context;

public class HomepageSteps {
	
	Context context;
	Object pageObject;
	HomePage homePage;
	ProductPage productPage;
	CheckoutPage checkoutPage;
	String bannerOption, searchedProduct, categoryName1, categoryName2, brandName1,brandName2;
	SoftAssert softAssert;
	int resultCount;

	
	public HomepageSteps(Context context) {
		this.context = context;
		homePage = new HomePage(WebDriverManager.getDriver());
		softAssert = new SoftAssert();
	}

	@Given("User is on Home Page")
	public void user_is_on_home_page() {
		Assert.assertEquals(homePage.getPageURL(), UIConstants.EXPECTED_URL_VALUE_HOMEPAGE);
	}

	@When("User clicks on the {string} banner")
	public void user_clicks_on_the_banner(String bannerOption) {
		pageObject = homePage.clickOnBannerOption(bannerOption);
		this.bannerOption = bannerOption;
	}

	@Then("User lands on {string}")
	public void user_should_be_redirected_to(String page) {

		switch (page.trim().toUpperCase()) {
		case "HOMEPAGE":
			homePage = (HomePage)pageObject;
			Assert.assertEquals(homePage.getPageURL(), UIConstants.EXPECTED_URL_VALUE_HOMEPAGE);
			break;
		
		case "CONTACTPAGE":
			ContactPage contactPage = (ContactPage)pageObject;
			Assert.assertTrue(contactPage.getPageURL().contains(UIConstants.PARTIAL_URL_VALUE_CONTACTPAGE));
			break;
		
		case "SIGN IN":
			LoginPage loginPage = (LoginPage)pageObject;
			Assert.assertTrue(loginPage.getPageURL().contains(UIConstants.PARTIAL_URL_VALUE_LOGINPAGE));
			break;
		}
	}
	
	@Then("User should see a drop down list with {int} options")
	public void user_should_see_a_drop_down_list_with_options(Integer optionCount) {
		
		/*
		 * System.out.println((Integer)homePage.getAllCategoriesOptions().get("Count"));
		 * System.out.println(homePage.getAllCategoriesOptions().get("List"));
		 * System.out.println(homePage.getAllCategoriesOptions().get("List").getClass().
		 * getSimpleName()); System.out.println(UIConstants.EXPECTED_CATEGORY_NAMES);
		 * System.out.println(UIConstants.EXPECTED_CATEGORY_NAMES.getClass().
		 * getSimpleName()); //ArrayList
		 */

		switch (bannerOption.trim()) {
		
		case "Categories":
			softAssert.assertEquals((Integer)homePage.getAllCategoriesOptions().get("Count"), optionCount);
			System.out.println(homePage.getAllCategoriesOptions().get("List"));
			softAssert.assertEquals(homePage.getAllCategoriesOptions().get("List"), UIConstants.EXPECTED_CATEGORY_NAMES);
		break;
		
		case "Language":
			softAssert.assertEquals((Integer)homePage.getAllLanguagesOptions().get("Count"), optionCount);
			System.out.println(homePage.getAllLanguagesOptions().get("List"));
			softAssert.assertEquals(homePage.getAllLanguagesOptions().get("List"), UIConstants.EXPECTED_LANGUAGE_OPTIONS);
		} 
		
		softAssert.assertAll();
	}
	
	
	/************************** SORTING **************************/
	@When("User selects {string} from the sort dropdown")
	public void user_selects_from_the_sort_dropdown(String dropdownOption) {

		homePage.selectPriceSortOption(dropdownOption);
	}
	
	@Then("User should see products displayed in {string}")
	public void user_should_see_products_displayed_in(String order) {
		
		String sortOrder = order.trim().toLowerCase();

		if(!sortOrder.contains("alphabetical")) {
			@SuppressWarnings("unchecked")
			List<Double> UI_SortedPrice = (List<Double>) homePage.getAllProduct_SpecificInfo("Price");
			List<Double> TestFramework_SortedPrice = homePage.sortPrice(sortOrder, UI_SortedPrice);

			Assert.assertEquals(UI_SortedPrice, TestFramework_SortedPrice);
		}
		else {
			
			System.out.println("*********************************************");
			
			@SuppressWarnings("unchecked")
			List<String> UI_SortedName = (List<String>) homePage.getAllProduct_SpecificInfo("Name");
			System.out.println(UI_SortedName);
			List<String> TestFramework_SortedName = homePage.sortName(sortOrder, UI_SortedName);
			System.out.println(TestFramework_SortedName);

			Assert.assertEquals(UI_SortedName, TestFramework_SortedName);
		}

	}
	
	/************************** SEARCH PRODUCT **************************/

	@When("User enters {string} in the search box and clicks the search button")
	public void user_enters_in_the_search_box_and_clicks_the_search_button(String productName) {
		
		searchedProduct = productName.trim().toLowerCase();
		homePage.enterTextInSearchBox(searchedProduct);
	}
	
	@Then("User should see a list of products matching {string}")
	public void user_should_see_a_list_of_products_matching(String productName) {
		
		@SuppressWarnings("unchecked")
		List<String> productNameList = (List<String>) homePage.getAllProduct_SpecificInfo("Name");
		System.out.println("Number of Products Found with Matching Name = "+productNameList.size());

		for(String e: productNameList) {
			System.out.println(e);
			softAssert.assertTrue(e.toLowerCase().contains(productName.toLowerCase()));
		}
		
		softAssert.assertAll();
	}
	
	@Then("User should see messages saying {string} and {string}")
	public void user_should_see_messages_saying_and(String expCaptionText, String expSearchResultText) {
	    
		Map <String,String> results = homePage.invalidSearchResult(searchedProduct);
		softAssert.assertEquals(results.get("searchCaption"), expCaptionText);
		softAssert.assertEquals(results.get("searchResult"), expSearchResultText);
		softAssert.assertAll();

	}
	
	/************************** CATEGORY-BASED FILTERING ***************************/
	
	@When("User selects the {string} category checkbox")
	public void user_selects_the_category_checkbox(String categoryName) {
		homePage.selectCheckbox(categoryName);
		context.setRuntimeData("subcategory", categoryName.trim());
	}

	@Then("User should see only products in the {string} category displayed")
	public void user_should_see_only_products_in_the_category_displayed(String categoryName) {
		@SuppressWarnings("unchecked")
		List<String> productNameList = (List<String>) homePage.getAllProduct_SpecificInfo("Name");
		System.out.println("Number of Products Found with Matching Name = "+productNameList.size());

		for(String e: productNameList) {
			System.out.println(e);
			softAssert.assertTrue(e.toLowerCase().contains(categoryName.toLowerCase()));
		}
		
		softAssert.assertAll();
	}
	
	@When("User selects {string} and {string} category checkboxes")
	public void user_selects_and_category_checkboxes(String categoryName1, String categoryName2) {
		this.categoryName1= categoryName1;
		this.categoryName2 = categoryName2;
		homePage.selectCheckbox(categoryName1);
		homePage.selectCheckbox(categoryName2);
	}
	
	@Then("User should see only products in the {string} and {string} categories displayed")
	public void user_should_see_only_products_in_the_and_categories_displayed(String categoryName1, String categoryName2) {

		@SuppressWarnings("unchecked")
		List<String> productNameList = (List<String>) homePage.getAllProduct_SpecificInfo("Name");
		resultCount = productNameList.size();

		for(String e: productNameList) {
			System.out.println(e);
			softAssert.assertTrue(e.toLowerCase().contains(categoryName1.toLowerCase()) || e.toLowerCase().contains(categoryName2.toLowerCase()));
		}
		
		softAssert.assertAll();
	}
	
	@When("User deselects all the categories")
	public void user_deselects_all_the_categories() {
		homePage.selectCheckbox(categoryName1);
		homePage.selectCheckbox(categoryName2);
	}
	
	@Then("All products should be displayed again")
	public void all_products_should_be_displayed_again() {
		
		@SuppressWarnings("unchecked")
		List<String> productNameList = (List<String>) homePage.getAllProduct_SpecificInfo("Name");
		int resultCountAfterDeselection = productNameList.size();
		System.out.println("Count after category selection = " + resultCount);
		System.out.println("Count after category DEselection = " + resultCountAfterDeselection);

		Assert.assertNotEquals(resultCount, resultCountAfterDeselection);
	}
	
	/************************** BRAND-BASED FILTERING ***************************/

	@When("User selects the {string} brand checkbox")
	public void user_selects_the_brand_checkbox(String brandName) {
		homePage.selectCheckbox(brandName);
		context.setRuntimeData("brandName", brandName.trim());
		
	}
	
	
	@Then("User should see only products in the {string} brand category displayed")
	public void user_should_see_only_products_in_the_brand_category_displayed(String brandName) {

		List<String> brandNameList = homePage.getAllBrandNames();
		for(String e: brandNameList) {
			softAssert.assertTrue(e.toLowerCase().contains(brandName.toLowerCase()));
		}
		
		softAssert.assertAll();
	}
	
	
	@When("User selects {string} and {string} brand checkboxes")
	public void user_selects_and_brand_checkboxes(String brandName1, String brandName2) {
		this.brandName1 = brandName1.trim();
		this.brandName2 = brandName2.trim();
		homePage.selectCheckbox(brandName1);
		homePage.selectCheckbox(brandName2);
	}

	
	@Then("User should see only products in the {string} and {string} brands displayed")
	public void user_should_see_only_products_in_the_and_brands_displayed(String brandName1, String brandName2) {

		List<String> brandNameList = homePage.getAllBrandNames();
		resultCount = brandNameList.size();
		for(String e: brandNameList) {
			softAssert.assertTrue(e.toLowerCase().contains(brandName1.toLowerCase()) || e.toLowerCase().contains(brandName2.toLowerCase()));
		}
		
		softAssert.assertAll();
	}
	
	@When("User deselects all the brand categories")
	public void user_deselects_all_the_brand_categories() {
		homePage.deselectBrandCheckbox(brandName1);
		homePage.deselectBrandCheckbox(brandName2);
	}
	
	@Then("All branded products should be displayed again")
	public void all_branded_products_should_be_displayed_again() {
		
		@SuppressWarnings("unchecked")
		List<String> productList =(List<String>) homePage.getAllProduct_SpecificInfo("Name");
		int resultCountAfterDeselection = productList.size();
		System.out.println("resultCount before deselection = " + resultCount);
		System.out.println("resultCountAfterDeselection = " + resultCountAfterDeselection);

		Assert.assertEquals(resultCount, resultCountAfterDeselection);
	}
	
	/************************** Complete Customer Flow ***************************/
	
	@Then("User clicks {string} image and views product details")
	public void user_clicks_image_and_views_product_details(String productName) {

		Map<String, Object> obj = homePage.viewProduct(productName.trim());		
		int productCardPrice = (int) obj.get("productPrice");
		productPage = (ProductPage)obj.get("pageObj");
		
		Map<String,Object> productDetailsFromProductPage = productPage.getProductDetails();
		softAssert.assertEquals(productName, String.valueOf(productDetailsFromProductPage.get("productName")));
		softAssert.assertEquals(productCardPrice, (int)productDetailsFromProductPage.get("productPrice"));
		softAssert.assertEquals(String.valueOf(context.getRuntimeData("subcategory")), String.valueOf(productDetailsFromProductPage.get("subcategory")));
		softAssert.assertEquals(String.valueOf(context.getRuntimeData("brandName")), String.valueOf(productDetailsFromProductPage.get("brand")));
		
		softAssert.assertAll();
		
		context.setRuntimeData("productName", productName);
		context.setRuntimeData("UnitPrice", productCardPrice);

	}
	
	@Then("User enters quantity and adds the product to the cart")
	public void user_enters_quantity_and_adds_the_product_to_the_cart() {
		
		int productQuantityOrdered = 2;
		productPage.setProductQuantity(String.valueOf(productQuantityOrdered));
		int cartQuantity = productPage.getCartQuantity();
		Assert.assertTrue(cartQuantity>=productQuantityOrdered);
		context.setRuntimeData("Quantity", productQuantityOrdered);
	}
	
	@Then("User sees product details in CartPage")
	public void user_sees_product_details_in_cart_page() {

		checkoutPage = productPage.navigateToCheckoutPage();
		Map<String, Map<String, Object>> productInfoFromCheckoutPage = checkoutPage.getAllProductInfo();
		String product = String.valueOf(context.getRuntimeData("productName"));
		Map<String, Object> info = productInfoFromCheckoutPage.get(product);
		
		softAssert.assertEquals((int)context.getRuntimeData("Quantity"), (int)info.get("Quantity"));
		softAssert.assertEquals((int)context.getRuntimeData("UnitPrice"), (int)info.get("UnitPrice"));
		softAssert.assertEquals((int)context.getRuntimeData("Quantity")*(int)context.getRuntimeData("UnitPrice"), (int)info.get("ProductTotalPrice"));
		
		softAssert.assertAll();
	}
	
	@Then("completes the checkout process")
	public void completes_the_checkout_process() {

		checkoutPage.clickCheckoutOrConfirmBtn("Proceed to checkout");
		
		//navigates to Signin step
		Register registeredUserInfo = (Register)context.getRuntimeData("userInfo");
		String firstName = registeredUserInfo.getFirstName();
		String lastName = registeredUserInfo.getLastName();
		String fullName = firstName+" "+lastName;
		String signedInText = checkoutPage.getSignedInText();
		softAssert.assertTrue(signedInText.contains(fullName));
		
		checkoutPage.clickCheckoutOrConfirmBtn("Proceed to checkout");
		
		//navigates to BillingAddress step
		Map<String, String> savedBillingInfo = checkoutPage.getBillingAddressInfo();
		softAssert.assertEquals(registeredUserInfo.getStreet(),savedBillingInfo.get("Street"));
		softAssert.assertEquals(registeredUserInfo.getCity(),savedBillingInfo.get("City"));
		softAssert.assertEquals(registeredUserInfo.getState(),savedBillingInfo.get("State"));
		softAssert.assertEquals(registeredUserInfo.getCountry(),savedBillingInfo.get("Country"));
		softAssert.assertEquals(registeredUserInfo.getPostalCode(),savedBillingInfo.get("Zipcode"));
		
		softAssert.assertAll();
		
		checkoutPage.clickCheckoutOrConfirmBtn("Proceed to checkout");
		
		//navigates to Payment step
		checkoutPage.selectPaymentMethod("Cash on Delivery");
		checkoutPage.clickCheckoutOrConfirmBtn("Confirm");
		//Payment was successful
		Assert.assertEquals(checkoutPage.getPaymentSuccessText(), "Payment was successful");
		
		//Sign out
		homePage = (HomePage)checkoutPage.goToMyPages(fullName, "Sign out");
		Assert.assertTrue(homePage.doesSearchBoxExist());
	}
}
