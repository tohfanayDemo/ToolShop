package hooks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.service.ExtentService;

import constants.Constants;
import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import logger.Log;
import utils.Context;
import utils.ExcelReader;
import utils.GlobalContext;
import utils.TestContextManager;



public class Hooks {

	WebDriver driver;
	DriverFactory driverFactory;
	Properties prop;
	GlobalContext globalContext = GlobalContext.getInstance(); 
	
	
	@BeforeAll
	public static void externalFIleOrAppSetUp() throws Exception {

		String excelPath = System.getProperty("user.dir") + Constants.EXCEL_FILE_PATH;
		if (excelPath != null) {

			ExcelReader.openExcel(excelPath);
		} 
	}
    
	@SuppressWarnings("unchecked") 
	@Before(value="@testData", order=1)
	public void testDataSetup(Scenario scenario) {
		
		List<Map<String, String>> testData = null;
		Collection<String> allTags = scenario.getSourceTagNames();
		
		for(String tagName: allTags) {
			String scenarioTagName = tagName.trim().toLowerCase();
			
		   switch (scenarioTagName) {
				case "@registration":
						
					synchronized (this) {
						
						testData = (List<Map<String, String>>) globalContext.getGlobalData("registrationTestDataTable");
						Log.info("Inside @Before(\"@testData\") for Registration");
						Log.info("is GlobalData 'testData' empty/null/available = " + testData + " for Registration"); 
						
						if (testData == null || testData.isEmpty()) {
							globalContext.setTestData(ExcelReader.getExcelData(Constants.REGISTRATION_EXCEL_SHEET_NAME));
							globalContext.setGlobalData("registrationTestDataTable", globalContext.getTestData());
						}
					}	
					break;
					
				case "@login":
					synchronized (this) {
						
						testData = (List<Map<String, String>>) globalContext.getGlobalData("loginTestDataTable");
						
						if (testData == null || testData.isEmpty()) {
							globalContext.setTestData(ExcelReader.getExcelData(Constants.LOGIN_EXCEL_SHEET_NAME)); 
							globalContext.setGlobalData("loginTestDataTable", globalContext.getTestData());
						}
					}	
					break;
					
				default:
					break;
		   }
		   
		}
	    
	}
	
	
	@Before(order=2)
	public void setup() {
		driverFactory = new DriverFactory();
		prop = driverFactory.initProp();	
		driver = driverFactory.initDriver(prop);

		ExtentService.getInstance().setSystemInfo("os","windows"); 
		globalContext.setGlobalData("propertiesObject", prop); 
		
		
	}
	
	@AfterStep
	public void takeScreenshotOnFailure(Scenario scenario) {
		
		if (scenario.isFailed()) {
			TakesScreenshot takesScreenshot = (TakesScreenshot) DriverFactory.getDriver();
			final byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
			
			//For Extent Report Attachment
			scenario.attach(screenshot, "image/png", screenshot.toString());
			//For Allure Report Attachment
			String currentScenarioName = scenario.getName();
			Allure.addAttachment(currentScenarioName,new ByteArrayInputStream(screenshot));
			
		}
	}
	
	
	@After
	public void tearDown() {
		
		
		DriverFactory.quitBrowser();
		
		//Empty current thread data
		Context context = TestContextManager.getContext();
		context.emptyRuntimeDataMap();
		TestContextManager.removeContext(); 	
	}
	
	@AfterAll
	public static void externalFIleOrAppTearDown() {
		try {
			// Close the Excel file
			ExcelReader.closeExcel();
			
			// Clear GlobalContext maps
			GlobalContext globalContext = GlobalContext.getInstance();
			globalContext.emptyTestDataMap();
			globalContext.emptyGlobalDataMap();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
