package runner;

import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/UI_Features",
		        glue = {"stepDefs", "hooks"},
		        tags = "@CompleteCustomer",
		        monochrome = true,
		        dryRun = true
)


public class testRunner_TestNG extends AbstractTestNGCucumberTests {

	    @DataProvider(parallel = false)
	    @Override
	    public Object[][] scenarios() { 
	    	return super.scenarios();
	    }
}
