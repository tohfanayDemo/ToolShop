package utils;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {
	

	private WebDriver driver;
	Alert alert;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}

	public void doClick(WebElement ele) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(ele));

			if (ele.isDisplayed()) {
				if (ele.isEnabled()) {

					try {

						ele.click();

					} catch (Exception e) {
						// JavascriptExecutor(driver) =
					}

				} else {
					throw new Exception("Element is not enabled");
				}
			} else {
				throw new Exception("Element is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void doClick(By locator) {

		try {

			WebElement ele = new WebDriverWait(driver, Duration.ofSeconds(120))
					.until(ExpectedConditions.visibilityOf(getElement(locator)));
			if(ele == null)
			{
				//LoggerLoad.info("ele is null");
			}
			else
			{
				//LoggerLoad.info("ele is not null"+ele.toString());
			}


			if (ele.isDisplayed()) {
				if (ele.isEnabled()) {

					try {

						ele.click();

					} catch (Exception e) {
						// JavascriptExecutor(driver) =
					}

				} else {
					//LoggerLoad.error("Element is not enabled");
					throw new Exception("Element is not enabled");
				}
			} else {
				//LoggerLoad.error("Element is not displayed");
				throw new Exception("Element is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public String getElementText(By locator) {
		elementWithFluentWaitLocated(locator, 20, 100);
		return getElement(locator).getText();
	}

	public String getElementText(WebElement ele) {
		return ele.getText();
	}

	public void setSelectOption(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}
	
	public void setSelectOptionByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);;
	}
	
	public void selectDropDOwnOption(String option) {
		By locator = By.xpath("//ul[@role='listbox']//*[text()='" + option + "']");
		elementWithFluentWaitClickable(locator, 30, 100).click();
	}

	public WebElement elementWithFluentWaitLocated(By locator, int timeOutInSeconds, int pollingIntervalInMillis) {

		WebElement ele = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOutInSeconds))
				.pollingEvery(Duration.ofMillis(pollingIntervalInMillis)).ignoring(NoSuchElementException.class)
				.until(ExpectedConditions.visibilityOfElementLocated(locator));

		return ele;

	}

	public WebElement elementWithFluentWaitClickable(By locator, int timeOutInSeconds, int pollingIntervalInMillis) {

		WebElement ele = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOutInSeconds))
				.pollingEvery(Duration.ofMillis(pollingIntervalInMillis))
				.ignoring(ElementClickInterceptedException.class)
				.until(ExpectedConditions.elementToBeClickable(locator));

		return ele;

	}

	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	public List<WebElement> getElements(By locator) {
		elementWithFluentWaitLocated(locator, 30, 100);
		return driver.findElements(locator);
	}
		

	public void doSendKeys(By locator, Keys keys) {
		WebElement ele = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOf(getElement(locator)));
		ele.sendKeys(keys);

	}

	public void doSendKeys(By locator, String text) {
		try {

			WebElement ele = new WebDriverWait(driver, Duration.ofSeconds(120))
					.until(ExpectedConditions.visibilityOf(getElement(locator)));

			if (ele.isDisplayed()) {
				if (ele.isEnabled()) {

					try {
						ele.click();
						ele.clear();
						//clearExistingText(ele);
						ele.sendKeys(text);
					} catch (Exception e) {
						System.out.println(e.getMessage()); 
					}

				} else {
					throw new Exception("Element is not enabled");
				}
			} else {
				throw new Exception("Element is not displayed");
			}

		} catch (Exception e) {
			// LoggerLoad.error("Exception Happened: "+e.getMessage());
		}

	}

	public int getElementSize(By locator) {

		int size = 0;
		try {

			List<WebElement> ele = new WebDriverWait(driver, Duration.ofSeconds(40))
					.until(ExpectedConditions.visibilityOfAllElements(getElements(locator)));
			size = ele.size();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return size;
	}

	public boolean isElementDisplayed(By locator) {
		boolean flag = false;
		try {
			
			Thread.sleep(3000);
			WebElement ele = new WebDriverWait(driver, Duration.ofSeconds(60))
					.until(ExpectedConditions.visibilityOf(getElement(locator)));

			if (ele.isDisplayed()) {

				flag = true;
			}

			else {
				throw new Exception("Element is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return flag;
	}
	
	public boolean isElementAvailable(By locator) {
		boolean flag = false;
		try {

			WebElement ele = new WebDriverWait(driver, Duration.ofSeconds(30))
					.until(ExpectedConditions.visibilityOf(getElement(locator)));

			if (ele!=null && ele.isDisplayed()) {

				flag = true;
			}

			else {
				return flag;
			}

		} catch (Exception e) {
			return flag;
			//e.printStackTrace();

		}
		return flag;
	}


	public void clearExistingText(WebElement ele) {
		ele.sendKeys(Keys.CONTROL);
		ele.sendKeys("A");
		ele.sendKeys(Keys.BACK_SPACE);

	}
	
    public void clearField(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
    }

	public String getAttributeVal(By locator, String attributeName) {
		return elementWithFluentWaitLocated(locator, 10, 100).getDomAttribute(attributeName);
	}

	public void attachFileUsingSendKeys(By locator, String filePath) {
		try {
			doSendKeys(locator, filePath);
		} catch (Exception e) {

		}
	}

	public void scrollToBottomOfPage() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_DOWN).build().perform();
	}

	public void scrollToTopOfPage() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_UP).build().perform();
	}
	
	public void scrollIntoView(By locator) {
		
	   ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", getElement(locator));     
	}

	public String getPageURL() {

		return driver.getCurrentUrl();
	}

	public String getPageTitle() {

		return driver.getTitle();
	}

	public void doSendkeysUsingJS(By locator, String codeString) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].value='" + codeString + "';", getElement(locator));

	}
	
	public boolean isFieldEditable(By locator) {
        WebElement editableElement = getElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean isContentEditable = (Boolean) js.executeScript("return arguments[0].isContentEditable;", editableElement);
		return isContentEditable;
	}
	public boolean ifFieldIsRequired(By locator) {
		WebElement ele = elementWithFluentWaitLocated(locator, 5, 100);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean isRequired = (Boolean) js.executeScript("return arguments[0].required;", ele);
		return isRequired;
	}

	public void clickAndDeleteTextUsingAction(By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(getElement(locator)).doubleClick().click().sendKeys(Keys.BACK_SPACE).perform();
	}

	public void mouseclickUsingAction(By locator) {
		Actions action = new Actions(driver);
		action.click(getElement(locator)).perform();
	}

	public void pressdownButtonUsingAction(By locator) {
		Actions action = new Actions(driver);
		action.keyDown(getElement(locator), Keys.ENTER).perform();
	}
	
	public void pressdownArrowButtonUsingAction() {
		Actions action = new Actions(driver);
		action
		.keyDown(Keys.DOWN)
		.keyUp(Keys.DOWN)
        .perform();
	}
	public void pressEnterButtonUsingAction() {
		Actions action = new Actions(driver);
		action
		.keyDown(Keys.ENTER)
		.keyUp(Keys.ENTER)
        .perform();
	}
	
	 public void pressKey(WebDriver driver, Keys key) {
	        // Initialize Actions class
	        Actions actions = new Actions(driver);

	        // Perform the key press action
	        actions.sendKeys(key).perform();
	 }


	public void clickElementByJS(By locator, WebDriver driver) {
		try {
			Thread.sleep(500);
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript("arguments[0].click();", getElement(locator));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}


	public boolean isElementEnabled(By locator) {
		elementWithFluentWaitLocated(locator, 30, 100);
		return getElement(locator).isEnabled();
	}
	

	public boolean isElementPresent(By locator) {
		try {
			driver.findElement(locator);
            return true;
		} catch (Exception e) {
            return false;

		}
        
    }

	 public void getPageLoadStatus() {
		 
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			
			String loadingStatus = js.executeScript("return document.readyState;").toString();
			
			//if page is loaded
			if(loadingStatus.equals("complete")) {
				//System.out.println("page is fully loaded");
			}
			else {
				
				int i=15;
				while (!(loadingStatus.equals("complete"))) {
					
					i++;
					loadingStatus = js.executeScript("return document.readyState;").toString();
					
					if(loadingStatus.equals("complete")) {
						break;
					}
				}
			}


	 }
	 
}
