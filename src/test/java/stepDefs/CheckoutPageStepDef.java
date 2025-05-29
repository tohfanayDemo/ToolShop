package stepDefs;

import java.util.Map;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import constants.Constants;
import driver.DriverFactory;
import io.cucumber.java.en.Then;
import models.Product;
import models.Register;
import pages.CheckoutPage;
import pages.HomePage;
import pages.ProductPage;
import utils.Context;
import utils.GlobalContext;
import utils.TestContextManager;

public class CheckoutPageStepDef {

	GlobalContext globalContext = GlobalContext.getInstance(); // ✅ Singleton access
	Context context = TestContextManager.getContext(); // ✅ Thread-local
	
	ProductPage productPage;
	CheckoutPage checkoutPage;
	HomePage homePage;
	SoftAssert softAssert;
	
	public CheckoutPageStepDef(){
		productPage = new ProductPage(DriverFactory.getDriver());
		softAssert = new SoftAssert();
	}
	
	@Then("User enters quantity and adds the product to the cart")
	public void user_enters_quantity_and_adds_the_product_to_the_cart() {
		
		int quantityOrdered = 2;
		productPage.setProductQuantity(String.valueOf(quantityOrdered));
		
		int cartQuantity = productPage.getCartQuantity();
		Assert.assertTrue(cartQuantity>=quantityOrdered);
		
		context.setRuntimeData("quantityOrdered", quantityOrdered);
	}
	
	@Then("User sees product details in CartPage")
	public void user_sees_product_details_in_cart_page() {

		checkoutPage = productPage.navigateToCheckoutPage();
		Map<String, Map<String, Object>> productInfoFromCheckoutPage = checkoutPage.getAllProductInfo();
		Product productFromProductPage = (Product)context.getRuntimeData("product");
		Map<String, Object> infoForGivenProduct = productInfoFromCheckoutPage.get(productFromProductPage.getName());
		
		//Verify Product Details from Product Page Matches Product Details in Cart page
		softAssert.assertEquals((int)context.getRuntimeData("quantityOrdered"), (int)infoForGivenProduct.get("Quantity"));
		softAssert.assertEquals(productFromProductPage.getPrice(), (int)infoForGivenProduct.get("UnitPrice"));
		softAssert.assertEquals((int)context.getRuntimeData("Quantity")*productFromProductPage.getPrice(), (int)infoForGivenProduct.get("ProductTotalPrice"));
		
		softAssert.assertAll();
	}
	
	@Then("completes the checkout process")
	public void completes_the_checkout_process() {
		
		//Step1: CartPage
		checkoutPage.clickCheckoutOrConfirmBtn("Proceed to checkout");
		
		//Step2: Sign-in step
		Register registeredUserInfo = (Register)context.getRuntimeData("userInfo");
		String fullName = registeredUserInfo.getFirstName()+" "+registeredUserInfo.getLastName();
		String signedInText = checkoutPage.getSignedInText();
		
		softAssert.assertTrue(signedInText.contains(fullName));
		
		checkoutPage.clickCheckoutOrConfirmBtn("Proceed to checkout");
		
		//Step3: BillingAddress step
		Map<String, String> savedBillingInfo = checkoutPage.getBillingAddressInfo();
		softAssert.assertEquals(registeredUserInfo.getStreet(),savedBillingInfo.get("Street"));
		softAssert.assertEquals(registeredUserInfo.getCity(),savedBillingInfo.get("City"));
		softAssert.assertEquals(registeredUserInfo.getState(),savedBillingInfo.get("State"));
		softAssert.assertEquals(registeredUserInfo.getCountry(),savedBillingInfo.get("Country"));
		softAssert.assertEquals(registeredUserInfo.getPostalCode(),savedBillingInfo.get("Zipcode"));
		
		softAssert.assertAll();
		
		checkoutPage.clickCheckoutOrConfirmBtn("Proceed to checkout");
		
		//Step4: Payment step
		checkoutPage.selectPaymentMethod("Cash on Delivery");
		checkoutPage.clickCheckoutOrConfirmBtn("Confirm");
		//Payment was successful
		Assert.assertEquals(checkoutPage.getPaymentSuccessText(), Constants.PAYMENT_SUCCESSFUL_TEXT);
		
		//Sign out
		homePage = (HomePage)checkoutPage.goToMyPages(fullName, "Sign out");
		Assert.assertTrue(homePage.doesSearchBoxExist());
	}
}
