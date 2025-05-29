package stepDefs;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;

import constants.Constants;
import driver.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Register;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.RegistrationPage;
import utils.Context;
import utils.GlobalContext;
import utils.TestContextManager;

public class LoginStepDef {
	
	
	GlobalContext globalContext = GlobalContext.getInstance(); // ✅ Singleton access
	Context context = TestContextManager.getContext(); // ✅ Thread-local
	Properties prop;
	HomePage homePage;
	LoginPage loginPage;
	MyAccountPage myAccountPage;
	RegistrationPage registrationPage;
	
	List<String> actualErrMsgList;
	Map<String, String> data;
	List<Map<String, String>> testDataTable;

	public LoginStepDef() {
		loginPage = new LoginPage(DriverFactory.getDriver());
		testDataTable = (List<Map<String, String>>) globalContext.getGlobalData("loginTestDataTable");
	}

	@Given("User is on Login Page")
	public void user_is_on_login_page() {
		Assert.assertTrue(loginPage.getPageURL().contains(Constants.PARTIAL_URL_VALUE_LOGINPAGE));
	}
	
	@When("User enters valid data in all field and clicks login button")
	public void user_enters_valid_data_in_all_field_and_clicks_login_button() {
		
		String registeredEmail =((Register)globalContext.getGlobalData("userInfo")).getEmail();
		String registeredPassword = ((Register)globalContext.getGlobalData("userInfo")).getPassword();
		
		if((registeredEmail==null || registeredEmail.isEmpty()) || (registeredPassword==null || registeredPassword.isEmpty())) {
			
			prop = (Properties)globalContext.getGlobalData("propertiesObject");
			registeredEmail = prop.getProperty("email");
			registeredPassword = prop.getProperty("password");
		}
		
		myAccountPage = loginPage.validLogin(registeredEmail, registeredPassword);
	}
	
	@Then("User should land on MyAccount page")
	public void user_should_land_on_MyAccount_page() {
		
		assertEquals(myAccountPage.getPageHeaderText(), Constants.HEADER_VALUE_MYACCOUNTPAGE);
	}
	
	@When("User clicks the {string} banner from MyAccount Page")
	public void user_clicks_the_banner_from_my_account_page(String bannerOption) {
		homePage = (HomePage)myAccountPage.clickOnBannerOption(bannerOption.trim());
	}
	
	/*********************** Validate login functionality with null user name ***************/
	
	@When("User enters value only in password and clicks login button")
	public void user_enters_value_only_in_password_and_clicks_login_button() {
		actualErrMsgList = loginPage.invalidLogin("", "Pwd123@@@!!!");
		
	}

	@Then("User should see error message {string}")
	public void user_should_see_error_message(String expectedErrMsg) {

		if(actualErrMsgList.size()==1) {
			Assert.assertEquals(actualErrMsgList.get(0), expectedErrMsg);
		}
	}

	/*********************** Validate login functionality with null user password ***************/
	
	@When("User enters value only in email address and clicks login button")
	public void user_enters_value_only_in_email_address_and_clicks_login_button() {
		actualErrMsgList = loginPage.invalidLogin("abc123@gmail.com", "");

	}

	/*********************** Validate login functionality with null credentials ***************/

	@When("User enters no values in email address and and password and clicks login button")
	public void user_enters_no_values_in_email_address_and_and_password_and_clicks_login_button() {
		actualErrMsgList = loginPage.invalidLogin("", "");

	}

	@Then("User should see both error messages {string} and {string}")
	public void user_should_see_both_error_messages_and(String expectedEmailErrMsg, String expectedPasswordErrMsg) {
		if(actualErrMsgList.size()==2) {
			Assert.assertEquals(actualErrMsgList.get(0), expectedEmailErrMsg);
			Assert.assertEquals(actualErrMsgList.get(1), expectedPasswordErrMsg);
		}
	}
	
	/*********************** Validate Registration link navigates user to Registration page ***************/
	
	@When("User clicks on the {string} link")
	public void user_clicks_on_the_link(String string) {

		registrationPage = loginPage.clickRegisterLink();
	}
	
	@Then("User is navigated to Registration Page")
	public void user_is_navigated_to_registration_page() {
		
		Assert.assertTrue(registrationPage.getPageURL().contains(Constants.PARTIAL_URL_VALUE_REGISTRATIONPAGE));
	}
	
	/******************* Login with Invalid Data *********************/
	
	@When("User enters value for {string} in email and password field")
	public void user_enters_value_for_in_email_and_password_field(String testCase) {
		
		data = context.getTestDataForTestCase(testDataTable, testCase.trim());
		System.out.println("=================================================");
	    for (Map.Entry<String, String> entry : data.entrySet()) {
	        System.out.println(entry.getKey() + " = " + entry.getValue());
	    }
	    System.out.println("=================================================");

		actualErrMsgList = loginPage.invalidLogin(data.get("Username"), data.get("Password"));
	}
	
	@Then("User should see proper error message")
	public void user_should_see_proper_error_message() {
		
		String expectedErrMsg = data.get("expectedErrorMsg").trim();
		String actualErrMsg = actualErrMsgList.get(0);
		
		Assert.assertEquals(expectedErrMsg, actualErrMsg);

	}
	
}
