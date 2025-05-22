package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.ElementUtil;

public class MyAccountPage extends Banner{

	private WebDriver driver;
	private ElementUtil util;
	
	private By pageHeader = By.xpath("//*[@data-test='page-title']");
	private By usernameMenu = By.xpath("//a[@id='menu']");

	
	public MyAccountPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		util = new ElementUtil(driver);
	}
	
	public String getPageHeaderText() {
		return util.getElementText(pageHeader);
	}
	
	public String getUsersName() {
		return util.getElementText(usernameMenu);
	}
	
	public HomePage clickCategoriesOption(String subCategory) {
		
		clickOnBannerOption("Categories");
		util.doClick(By.linkText(subCategory));
		
		return new HomePage(driver);		
	}
}
