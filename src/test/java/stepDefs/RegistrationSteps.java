package stepDefs;

import java.util.Map;

import org.testng.Assert;
import driver.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
		 registrationPage.enterFormData("Invalid", data.get("FirstName"), data.get("LastName"), data.get("DOB"), data.get("Street"), 
				 data.get("PostalCode"), data.get("City"), data.get("State"), data.get("Country"), data.get("Phone"),
				 data.get("Email"), data.get("Password"));
	}
	
	
	@Then("User should see error message")
	public void user_should_see_error_message() {

		String expectedErrMsg = data.get("Message").trim();		
		String actualErrMsg = registrationPage.getErrMsgText();

		Assert.assertEquals(expectedErrMsg, actualErrMsg);
	}

	
	
}
