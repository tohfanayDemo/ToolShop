package stepDefs;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import constants.Constants;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import driver.DriverFactory;
import models.Register;
import pages.LoginPage;
import pages.RegistrationPage;
import utils.Context;
import utils.DynamicDataGenerator;
import utils.GlobalContext;
import utils.TestContextManager;

public class RegistrationStepDef {
	
	GlobalContext globalContext = GlobalContext.getInstance(); 
	Context context = TestContextManager.getContext(); 
	
	RegistrationPage registrationPage;
	LoginPage loginPage;
	
	Map<String, String> data;
	List<Map<String, String>> testDataTable;
	
	
	public RegistrationStepDef(){
		registrationPage = new RegistrationPage(DriverFactory.getDriver());
		testDataTable = (List<Map<String, String>>) globalContext.getGlobalData("registrationTestDataTable");
	}
	
	/********************** Empty Form *****************************/
	
	@When("User clicks on Register button without filling up form")
	public void user_clicks_on_register_button_without_filling_up_form() {

		registrationPage.clickRegisterBtn();
	}
	
	@Then("User sees error msg for each field")
	public void user_sees_error_msg_for_each_field() {

		int formFieldCount = registrationPage.getFormFieldCount();
		int totalErrMsgCount = registrationPage.getEmptyFormErrMsgCount();

		Assert.assertEquals(formFieldCount, totalErrMsgCount);

	}

	/********************** Password Strength *****************************/
	
	@When("User enters value for {string} in password field")
	public void user_enters_value_for_in_password_field(String testCase) {
		
		
	    data = context.getTestDataForTestCase(testDataTable,testCase.trim());
	    
	    System.out.println("=================================================");
	    for (Map.Entry<String, String> entry : data.entrySet()) {
	        System.out.println(entry.getKey() + " = " + entry.getValue());
	    }
	    System.out.println("=================================================");

	    registrationPage.enterPasswordValue(data.get("Password"));
	}
	
	@Then("User should see password strength bar and label to be {string}")
	public void user_should_see_password_strength_bar_and_label_to_be(String level) {
		Map<String, Boolean> values = registrationPage.getPasswordStrengthAndLabelValueActivationStatus(level);
		Assert.assertTrue(values.get("strengthLabel"));
		Assert.assertTrue(values.get("strengthBar"));

	}
	
	/********************** Register with Invalid Data *****************************/
	
	@When("User enters for data in form for scenario {string}")
	public void user_enters_for_data_in_form_for_scenario(String testCase) {

		data = context.getTestDataForTestCase(testDataTable, testCase.trim());
		
		//build register object
		Register register = new Register.RegisterBuilder()
		.setFirstName(data.get("FirstName"))
		.setLastName(data.get("LastName"))
		.setDob(data.get("DOB"))
		.setStreet(data.get("Street"))
		.setPostalCode(data.get("PostalCode"))
		.setCity(data.get("City"))
		.setState(data.get("State"))
		.setCountry(data.get("Country"))
		.setPhone(data.get("Phone"))
		.setEmail(data.get("Email"))
		.setPassword(data.get("Password"))
		.build(); 
		
		registrationPage.enterFormData("Invalid", register);

	}
	
	
	@Then("User should see error message")
	public void user_should_see_error_message() {

		String expectedErrMsg = data.get("Message").trim();		
		String actualErrMsg = registrationPage.getErrMsgText();
		Assert.assertEquals(expectedErrMsg, actualErrMsg);
	}

	/********************** Register with Valid Data *****************************/
	
	@When("User clicks on Register button with valid data")
	public void user_clicks_on_register_button_with_valid_data() {
	    
		String userEmail = "toffee"+DynamicDataGenerator.getRandomNumberWithin(999)+"@gmail.com";
		String userPassword = "DuubDub$26" + DynamicDataGenerator.getRandomNumberWithin(99);

		Register register = new Register.RegisterBuilder()
				.setFirstName("Tohfa")
				.setLastName("Nay")
				.setDob("10101991")
				.setStreet("124 Street") 
				.setPostalCode("11211") 
				.setCity("Brooklyn") 
				.setState("NY")
				.setCountry("US")
				.setPhone("9728657789")
				.setEmail(userEmail)
				.setPassword(userPassword)
				.build(); 
				
		loginPage =	(LoginPage)registrationPage.enterFormData("Valid", register);

		globalContext.setGlobalData("userInfo",register);

	}
	
	@Then("User is navigated to Login Page")
	public void user_is_navigated_to_login_page() {
	    
		Assert.assertEquals(loginPage.getPageHeaderText(), Constants.HEADER_VALUE_LOGINPAGE);
	}
	
}
