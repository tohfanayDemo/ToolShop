package pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.ElementUtil;

public class ProductPage extends Banner{

	private WebDriver driver;
	private ElementUtil util;
	
	private By availabilityStatus = By.xpath("//*[@data-test='out-of-stock']");
	private By productName = By.xpath("//*[@data-test='product-name']");
	private By subcategory = By.xpath("//*[@aria-label='brand']");
	private By brand = By.xpath("//*[@aria-label='brand']");
	private By price = By.xpath("//span[@data-test='unit-price']");
	private By decreaseBtn = By.id("btn-decrease-quantity");
	private By increaseBtn = By.id("btn-increase-quantity");
	private By quantity = By.id("quantity-input");
	private By addToCartBtn = By.id("btn-add-to-cart");
	private By addToCartSuccessText = By.xpath("//div[@role='alert' and normalize-space()='Product added to shopping cart.']");
	private By addToFavourites  = By.id("btn-add-to-favorites");
	private By cartQuantity  = By.xpath("//span[@data-test='cart-quantity']"); //can click it and also get text of quantity
	

	public ProductPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		util = new ElementUtil(driver);
	}
	
	public void setProductQuantity(String quantity) {
		util.doSendKeys(this.quantity, quantity);
	}
	
	public int getCartQuantity() {
		return Integer.parseInt(util.getElementText(cartQuantity));
	}
	
	public CheckoutPage navigateToCheckoutPage() {
		util.doClick(cartQuantity);
		return new CheckoutPage(driver);
	}
	
	public void clickButtonName(String buttonName) {
		String button = buttonName.trim();
		if(button.equalsIgnoreCase("increase")) {
			util.doClick(increaseBtn);
		}
		else if(button.equalsIgnoreCase("decrease")) { 
			util.doClick(decreaseBtn);
		}
		else util.doClick(addToFavourites);
	}
	
	public String addToCart() {
		
		String productStatus = null;
		if(util.isElementPresent(availabilityStatus)) {
			productStatus = util.getElementText(availabilityStatus);
		}
		else {
			util.doClick(addToCartBtn);
			WebElement successMsg = util.getElement(addToCartSuccessText);
			productStatus = successMsg.getText();
		}
		return productStatus;
	}
	
	public Map<String,Object> getProductDetails() {
		
		Map<String,Object> productInfo = new HashMap<String,Object>();
		productInfo.put("productName", util.getElementText(productName).trim());
		productInfo.put("subcategory", util.getElementText(subcategory).trim());
		productInfo.put("brand", util.getElementText(brand).trim());
		String[] price = util.getElementText(this.price).trim().split("$");
		productInfo.put("productPrice", Integer.parseInt(price[1])); 
		
		return productInfo;
	}
}
