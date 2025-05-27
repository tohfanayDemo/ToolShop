package driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import logger.Log;

public class WebDriverManager {
	
	WebDriver driver;
	Properties prop;
	
	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	
	public WebDriver initDriver(Properties prop, String testNGBrowser) {
		
		//Choose source of browser value
		String browser = null;
		String envBrowser = System.getProperty("browser");
		
		if(envBrowser != null) {//1st priority env value
			browser = envBrowser;
		}
		else if(testNGBrowser != null) {//2nd priority testNG value
			browser = testNGBrowser;
		}
		else {//default properties file value
			browser = prop.getProperty("browser");
		}
		
		
		switch (browser.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver());
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver());
			break;
		case "edge":
			tlDriver.set(new EdgeDriver());
			break;	
		default:
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
		
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
	}
	

	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public static void quitBrowser() {
		if(tlDriver.get() != null) {
			tlDriver.get().quit();
			tlDriver.remove();
		}
	}
	
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		Log.info("Running tests on env:  " + envName);

		try {
			if (envName == null) {
				Log.warn("env is null....hence running tests on QA env");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
					break;

				default:
					Log.error("plz pass the right env name..." + envName);
					throw new IllegalArgumentException("INVALID ENV NAME");
					//throw new FrameworkException("INVALID ENV NAME");
				}
			}

			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

}
