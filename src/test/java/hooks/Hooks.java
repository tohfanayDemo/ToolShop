package hooks;

import java.io.IOException;
import java.util.Collection;

import org.openqa.selenium.WebDriver;

import driver.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import utils.Context;
import utils.ExcelReader;


public class Hooks {

	WebDriver driver;
	WebDriverManager driverManager;
	Context context;
	
	public Hooks(Context context) {
		this.context = context;
	}
	
	@BeforeAll
	public static void externalFIleOrAppSetUp() throws Exception {

		String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\testdata.xlsx";
		System.out.println("Excel file path = " + excelPath);
		if (excelPath != null) {

			ExcelReader.openExcel(excelPath);
			System.out.println("Excel file opened successfully.");
		} /*
			 * else System.out.println("No excel path found");
			 */
	}
       
	@Before("@testData")
	public void testDataSetup(Scenario scenario) {
		
	    // Get all tags
		Collection<String> allTags = scenario.getSourceTagNames();
		//System.out.println("All tags in this scenario = " + allTags);
		
		for(String tagName: allTags) {
			String scenarioTagName = tagName.trim().toLowerCase();
			
		   switch (scenarioTagName) {
				case "@registration":
					context.setTestData(ExcelReader.getExcelData("Registration")); 
					break;
					
				case "@login":
					context.setTestData(ExcelReader.getExcelData("Login"));   
					break;
				default:
					break;
		   }
		   
		}
	    
	}
	
	@Before()
	public void chromeSetUp() {
		System.out.println("I am in chrome before scenario");
		String browser = "chrome";
		driverManager = new WebDriverManager();
		driver = driverManager.initDriver(browser);
		driver.get("https://practicesoftwaretesting.com");
	}
	
	@After
	public void quitBrowser() {
		WebDriverManager.quitBrowser();
		context.emptyDataMap();
	}
	
	@AfterAll
	public static void externalFIleOrAppTearDown() {
		try {
			
			// Close the Excel file
			ExcelReader.closeExcel();
			System.out.println("Excel file closed successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
