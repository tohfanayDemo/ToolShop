package hooks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import driver.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

import io.qameta.allure.Allure;
import utils.ConfigReader;
import utils.Context;
import utils.ExcelReader;
import utils.TestContextManager;


public class Hooks {

	WebDriver driver;
	WebDriverManager driverManager;
	//Context context;
	Properties prop;
	
	public Hooks(/*Context context*/) {
		//this.context = context;
	}
	
	@BeforeAll
	public static void externalFIleOrAppSetUp() throws Exception {

		String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\testdata.xlsx";
		if (excelPath != null) {

			ExcelReader.openExcel(excelPath);
		} 
	}
       
	@Before("@testData")
	public void testDataSetup(Scenario scenario) {
		
		Context context = TestContextManager.getContext();
		Collection<String> allTags = scenario.getSourceTagNames();
		
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
	public void setup() {
		System.out.println("I am in setup before scenario");
		driverManager = new WebDriverManager();
		prop = driverManager.initProp();
		driver = driverManager.initDriver(prop,ConfigReader.getBrowserFromTestNG());
		
	}
	
	@After
	public void tearDown(Scenario scenario) {
		
		if (scenario.isFailed()) {
			final byte[] screenshot = ((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "Myscreenshot");
			Allure.addAttachment("Myscreenshot",
					new ByteArrayInputStream(((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES)));
		}
		
		WebDriverManager.quitBrowser();
		Context context = TestContextManager.getContext();
		context.emptyDataMap();
		context.emptyRuntimeDataMap();
		TestContextManager.removeContext(); // Cleanup thread-local contex
	}
	
	@AfterAll
	public static void externalFIleOrAppTearDown() {
		try {
			// Close the Excel file
			ExcelReader.closeExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
