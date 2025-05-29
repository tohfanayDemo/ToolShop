package runner;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import driver.DriverFactory;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features",
		        glue = {"stepDefs", "hooks"},
		        monochrome = true,
		        dryRun = false,
		        plugin = {"pretty","json:target/cucumber-reports/Cucumber.json",
						"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)


public class TestRunner extends AbstractTestNGCucumberTests {

	
	    @DataProvider(parallel = true)
	    @Override
	    public Object[][] scenarios() { 
	    	return super.scenarios();
	    }
	    
	   /* 
	    @BeforeClass
	    public void beforeClass(ITestContext context) {
	    	System.out.println("before class");
	    	context.getCurrentXmlTest().getSuite().setDataProviderThreadCount(3);
	    }*/

	    
	    @BeforeTest
	    @Parameters({"browser"})
		public void defineBrowser(@Optional String browser) {
	    	
	    	DriverFactory driverFactory = new DriverFactory();
	    	driverFactory.setTestNGBrowserType(browser);
		}
}
