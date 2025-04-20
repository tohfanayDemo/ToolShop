package pages;

import org.openqa.selenium.WebDriver;

import utilities.ElementUtil;

public class ForgotPasswordPage extends Banner{
	
	private WebDriver driver;
	private ElementUtil util;
	
	public ForgotPasswordPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		util = new ElementUtil(driver);
	}
}
