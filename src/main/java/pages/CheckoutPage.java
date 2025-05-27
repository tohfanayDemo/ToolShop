package pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.ElementUtil;

public class CheckoutPage extends Banner{

	private WebDriver driver;
	private ElementUtil util;
	
	private By stepsSection = By.xpath("//ul[@class='steps-4 steps-indicator']");
	private By totalRows = By.xpath("//tbody/tr");
	private By columnHeaders = By.xpath("//thead/tr/th");
	private By cartTotalField = By.xpath("//tfoot//td[@data-test='cart-total']");
	private By signedInText = By.xpath("//app-login//p[@class='ng-star-inserted']");
	private By billingAddressForm = By.xpath("//h3[text()='Billing Address']//following-sibling::form");
	private By selectPayment = By.id("payment-method");
	private By paymentSuccessfulText = By.xpath("//div[@data-test='payment-success-message']");
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		util = new ElementUtil(driver);
	}
	
	public boolean ifStepsSectionExists() {
		return util.isElementPresent(stepsSection);
	}
	
	public List<String> getColumnHeadersList() {
		List<String> colHeaderNames = new ArrayList<String>();
		List<WebElement> list = util.getElements(columnHeaders);
		
		for(WebElement ele: list) {
			colHeaderNames.add(ele.getText());
		}
		return colHeaderNames;
	}
	
	
	public Map<String, Map<String, Object>> getAllProductInfo() {
		
		Map<String, Map<String, Object>> cart = new HashMap<>();
		
		int totalRows = util.getElementSize(this.totalRows);
		
		for(int i=1; i<=totalRows; i++) {
			By itemField = By.xpath("(//tbody/tr)["+i+"]//span[@data-test='product-title']");
			By quantityField = By.xpath("(//tbody/tr)["+i+"]//input[@data-test='product-quantity']");
			By priceField = By.xpath("(//tbody/tr)["+i+"]//span[@data-test='product-price']");
			By itemTotalField = By.xpath("(//tbody/tr)["+i+"]//span[@data-test='line-price']");
			
			//get Product Name
			String productName = util.getElementText(itemField);
			
			//get Product Quantity
			String quantityOrdered = util.getElementText(quantityField);
			
			//get Product unit price
			String[] productUnitPrice = util.getElementText(priceField).split("$");
			int unitPrice = Integer.parseInt(productUnitPrice[1]);
			
			//get Product Total
			String[] productTotalPrice = util.getElementText(itemTotalField).split("$");
			int unitTotalPrice = Integer.parseInt(productTotalPrice[1]);
			
			Map<String, Object> productInfo = new HashMap<>();
			productInfo.put("Quantity", quantityOrdered);
		    productInfo.put("UnitPrice", unitPrice);
		    productInfo.put("ProductTotalPrice", unitTotalPrice);

		    cart.put(productName, productInfo);
		}
		
		return cart;
	}
	
	public int getTotalPrice() {
		String[] cartTotal = util.getElementText(cartTotalField).split("$");
		return Integer.parseInt(cartTotal[1]);
	}
	
	public void clickCheckoutOrConfirmBtn(String buttonName) {
		
		By button = By.xpath("//button[normalize-space()='"+buttonName+"']");
		util.doClick(button);
	}
	
	public String getSignedInText() {
		return util.getElementText(signedInText);
	}
	
	public Map<String, String> getBillingAddressInfo() {
		
		Map<String, String> savedBillingInfo = new HashMap<String, String>();
		WebElement form = util.getElement(billingAddressForm);
		List<WebElement> info = form.findElements(By.xpath("//input"));
		
		savedBillingInfo.put("Street", info.get(0).getText().trim());
		savedBillingInfo.put("City", info.get(1).getText().trim());
		savedBillingInfo.put("State", info.get(2).getText().trim());
		savedBillingInfo.put("Country", info.get(3).getText().trim());
		savedBillingInfo.put("Zipcode", info.get(4).getText().trim());
		
		return savedBillingInfo;

	}
	
	public void selectPaymentMethod(String paymentMethod) {
		util.setSelectOption(selectPayment, paymentMethod.trim());;
	}
	
	public String getPaymentSuccessText() {
		return util.getElementText(paymentSuccessfulText);
	}

	

}
