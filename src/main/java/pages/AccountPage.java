package pages;

import org.openqa.selenium.WebDriver;

import utilities.ElementUtil;

public class AccountPage extends Banner{

	private WebDriver driver;
	private ElementUtil util;
	
	public AccountPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		util = new ElementUtil(driver);
	}
}
