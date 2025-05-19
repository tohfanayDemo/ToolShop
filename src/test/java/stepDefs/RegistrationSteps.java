package stepDefs;

import java.util.Map;

import org.testng.Assert;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import driver.WebDriverManager;
import models.Register;
import pages.RegistrationPage;
import utilities.Context;

public class RegistrationSteps {
	
	private RegistrationPage registrationPage;
	Context context;
	Map<String, String> data;
	
	public RegistrationSteps(Context context){
		this.context = context;
		registrationPage = new RegistrationPage(WebDriverManager.getDriver());
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
	public void user_enters_value_for_in_password_field(String testName) {

	    Map<String, String> data = context.getTestDataForTestCase(testName);
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

		data = context.getTestDataForTestCase(testCase.trim());
		
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
		System.out.println("Actual Error Msg Received = " + actualErrMsg);
		Assert.assertEquals(expectedErrMsg, actualErrMsg);
	}

	
	
}
