package pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utilities.ElementUtil;

public class RegistrationPage extends Banner {

	private WebDriver driver;
	private ElementUtil util;

	private By firstNameField = By.id("first_name");
	private By lastNameField = By.id("last_name");
	private By dobField = By.id("dob");
	private By streetField = By.id("street");
	private By postalCodeField = By.id("postal_code");
	private By cityField = By.id("city");
	private By stateField = By.id("state");
	private By countryDropdown = By.id("country");
	private By phoneField = By.id("phone");
	private By emailField = By.id("email");
	private By passwordField = By.id("password");
	private By registerButton = By.xpath("//button[normalize-space()='Register']");

	private By passwordCondition_8character = By.xpath("//li[normalize-space()='Be at least 8 characters long']");
	private By passwordCondition_letters = By
			.xpath("//li[normalize-space()='Contain both uppercase and lowercase letters']");
	private By passwordCondition_numbers = By.xpath("//li[normalize-space()='Include at least one number']");
	private By passwordCondition_specialCharacter = By
			.xpath("//li[normalize-space()='Have at least one special symbol (e.g., @, #, $, etc.)']");

	private By formEditFields = By.xpath("//form//input");
	private By emptyFormErrMsgs = By.xpath("//form//div[contains(@class,'alert-danger')]");

	public RegistrationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		util = new ElementUtil(driver);
	}

	public void enterPasswordValue(String pwdText) {

		String password = pwdText.trim();
		util.scrollIntoView(passwordField);
		util.doSendKeys(passwordField, password);

	}

	public Map<String, Boolean> getPasswordStrengthAndLabelValueActivationStatus(String level) {

		By passwordStrengthLabel = By.xpath("//div[@class='strength-labels']/span[text()='" + level.trim() + "']");
		String classAttr_strengthLabel = util.getElement(passwordStrengthLabel).getDomAttribute("class");

		boolean strengthLabel = false;
		boolean strengthBar = false;

		if (classAttr_strengthLabel != null && classAttr_strengthLabel.equals("active")) {
			System.out.println("Element has a 'class' attribute with value: " + classAttr_strengthLabel);
			strengthLabel = true;
		}

		By passwordStrengthBar = By.xpath("//div[@class='strength-bar']/div");
		String classAttr_strengthBar = util.getElement(passwordStrengthBar).getDomAttribute("class");

		if (level.equalsIgnoreCase("Very Strong")) {
			level = level.replace(" ", "-");
		}

		if (classAttr_strengthBar != null && classAttr_strengthBar.equals("fill " + level.trim().toLowerCase())) {
			System.out.println("Element has a 'class' attribute with value: " + classAttr_strengthBar);
			strengthBar = true;
		}

		Map<String, Boolean> values = new HashMap<String, Boolean>();
		values.put("strengthLabel", strengthLabel);
		values.put("strengthBar", strengthBar);

		System.out.println(values.get("strengthLabel"));
		System.out.println(values.get("strengthBar"));

		return values;

	}

		
	public Object enterFormData(String validOrInvalid, String fName, String lName, String DOB, String street, String postCode, String city, String state, 
			  String country, String phoneNum, String emailID, String password) {
		  
		  util.doSendKeys(firstNameField, fName.trim());
		  util.doSendKeys(lastNameField, lName.trim());
		  util.doSendKeys(dobField, DOB.trim());
		  util.doSendKeys(streetField, street.trim());
		  util.scrollIntoView(phoneField);
		  util.doSendKeys(postalCodeField, postCode.trim());
		  util.doSendKeys(cityField, city.trim());
		  util.doSendKeys(stateField, state.trim());
		  
		  if(!country.isBlank() || !country.isEmpty()) {
			  util.setSelectOptionByValue(countryDropdown, country.trim()); 
		  }
		  
		  util.doSendKeys(phoneField, phoneNum.trim());
		  util.doSendKeys(emailField, emailID.trim());
		  util.doSendKeys(passwordField, password.trim());
		  
		  clickRegisterBtn();
		  
		  if(validOrInvalid.equalsIgnoreCase("valid")) {
			  return new AccountPage(driver);
		  } else return null;
	  }
	 

	public void clickRegisterBtn() {
		
		try {
			util.scrollToBottomOfPage();
			util.doClick(registerButton);
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getFormFieldCount() {

		int inputFieldCount = util.getElementSize(formEditFields);
		if (util.isElementPresent(countryDropdown)) {
			inputFieldCount++;
		}

		return inputFieldCount;
	}

	public int getEmptyFormErrMsgCount() {

		return util.getElementSize(emptyFormErrMsgs);
	}
	
	public String getErrMsgText() {
		
		String errText = null;
		if(getEmptyFormErrMsgCount() == 1) {
			errText = util.getElementText(emptyFormErrMsgs);
		}
		
		return errText.trim();
	}

}
