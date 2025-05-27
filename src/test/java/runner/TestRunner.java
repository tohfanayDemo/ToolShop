package runner;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utils.ConfigReader;

@CucumberOptions(features = "src/test/resources/features",
		        glue = {"stepDefs", "hooks"},
		        tags = "@Banner",
		        monochrome = true,
		        dryRun = false,
		        plugin = {"pretty","json:target/cucumber-reports/Cucumber.json",
						"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
						"rerun:target/failed.txt"}
)


public class TestRunner extends AbstractTestNGCucumberTests {

	    @DataProvider(parallel = true)
	    @Override
	    public Object[][] scenarios() { 
	    	return super.scenarios();
	    }
	    
	    @BeforeTest
		@Parameters({"browser"})
		public void defineBrowser(@Optional String browser) {
	        ConfigReader.setBrowserFromTestNG(browser);
		}
}
