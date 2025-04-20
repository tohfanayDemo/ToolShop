package pages;

import org.openqa.selenium.WebDriver;

import utilities.ElementUtil;

public class ContactPage extends Banner{

	private WebDriver driver;
	private ElementUtil util;
	
	public ContactPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		util = new ElementUtil(driver);
	}
}
