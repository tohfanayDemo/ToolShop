package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.ElementUtil;

public class LoginPage extends Banner{

	private WebDriver driver;
	private ElementUtil util;
	
	private By emailField = By.id("email");
	private By passwordField = By.id("password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By registerLink = By.xpath("//a[text()='Register your account']");
	private By forgotYourPwdLink = By.xpath("//a[text()='Forgot your Password?']");
	private By errMsgs = By.xpath("//*[@class='alert alert-danger']");
	private By emptyFormErrMsgs = By.xpath("//form//div[contains(@class,'alert-danger')]/div");

	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		util = new ElementUtil(driver);
	}
		
	private void clickLoginBtn() {
		util.doClick(loginButton);
	}
	
	private void enterValue(String fieldName, String value) {
		if(fieldName.trim().equalsIgnoreCase("email")) {
			util.doSendKeys(emailField, value);
		}else util.doSendKeys(passwordField, value);
	}
	
	public HomePage validLogin(String emailId, String password) {
		enterValue("email",emailId);
		enterValue("password",password);
		clickLoginBtn();
		return new HomePage(driver);
	}
	
	public List<String> invalidLogin(String emailId, String password) {
		enterValue("email",emailId);
		enterValue("password",password);
		clickLoginBtn();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> errMsgTextList = new ArrayList<String>();
		
		if(util.getElementSize(errMsgs)>0) {
			
			List<WebElement> errMsgsList = util.getElements(errMsgs);
			for(WebElement e: errMsgsList) {
				errMsgTextList.add(util.getElementText(e));
			}
		}
		
		return errMsgTextList;
	}
	
	public RegistrationPage clickRegisterLink() {
		util.doClick(registerLink);
		return new RegistrationPage(driver);
	}
	
	public ForgotPasswordPage clickForgotPasswordLink() {
		util.doClick(forgotYourPwdLink);
		return new ForgotPasswordPage(driver);
	}
}
