package pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.ElementUtil;

public abstract class Banner {
	
	private WebDriver driver;
	private ElementUtil util;
	
	By dropdownSection_Categories = By.xpath("//ul[contains(@class,'dropdown-menu')][@aria-label='nav-categories']");
	By dropdownSection_Langugages = By.xpath("//ul[@id='dropdown-animated']");

	protected Banner(WebDriver driver) {// Prevents instantiation from outside this class or subclasses
		this.driver = driver;
		util = new ElementUtil(driver);
	}
	
	public String getPageURL() {
		try {
			Thread.sleep(4000);
			util.getPageLoadStatus();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return util.getPageURL();
	}
	
	public Object clickOnBannerOption(String optionName) {
		By bannerOptionLocator = By.linkText(optionName);
		Object pageObj = null;
		
		switch (optionName.trim()) {
		case "Home":
			util.doClick(bannerOptionLocator);
			pageObj = new HomePage(driver);
			
		case "Contact":
			util.doClick(bannerOptionLocator);
			pageObj = new ContactPage(driver);
			
		case "Sign in":
			util.doClick(bannerOptionLocator);
			pageObj = new LoginPage(driver);	
		
		case "Categories":
			util.doClick(bannerOptionLocator);
			break;
			
		case "Language":
			bannerOptionLocator = By.id("language");
			util.doClick(bannerOptionLocator);
			break;
			
		default:
			System.out.println("Banner option not found");
			break;
		}
		
		return pageObj;
	}
	
	public Map<String, Object> getAllCategoriesOptions() {

		return getDropdownListOptions(dropdownSection_Categories);
	}
	
	public Map<String, Object> getAllLanguagesOptions() {

		return getDropdownListOptions(dropdownSection_Langugages);
	}
	
	private Map<String, Object> getDropdownListOptions(By dropdownSection) {
		
		List<String> optionsTextList = new ArrayList<String>();
				
		if(util.isElementDisplayed(dropdownSection)) {
			
			List<WebElement> eleList = util.getElement(dropdownSection).findElements(By.xpath(".//a"));
			for(WebElement e: eleList) {
				optionsTextList.add(e.getText());
			}
			
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("List", optionsTextList);
		data.put("Count", optionsTextList.size());

		return data;
	}

}
