package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import constants.PriceSort;
import utils.ElementUtil;

public class HomePage extends Banner{
	
	private WebDriver driver;
	private ElementUtil util;
	private PriceSort priceSort;
	String baseURL = "https://practicesoftwaretesting.com";
	
	private By sortDropdown = By.xpath("//select[@aria-label='sort']");
	private By paginationBar = By.xpath("//ul[@class='pagination']");
	private By paginationBarBoxes = By.xpath("//ul[@class='pagination']/li");
	private By nextPageBtn = By.xpath("//a[@aria-label='Next' and @role='button']");
	private By productGrid = By.xpath("(//div[@class='container'])[3]");
	private By priceSlider; //tbd
	private By searchBox = By.id("search-query");
	private By searchButton = By.xpath("//button[text()='Search']");
	private By invalidSearchTextCaption = By.xpath("//h3[@data-test='search-caption']");
	private By invalidSearchTextResult = By.xpath("//div[@data-test='no-results']");
	private By brandName = By.xpath("//span[@aria-label='brand']");


	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		util = new ElementUtil(driver);
	}
	
	public boolean doesSearchBoxExist() {
		return util.isElementPresent(searchBox);
	}
	
	public void selectPriceSortOption(String option) {
		
		String value = null;
		switch (option.trim().toLowerCase()) {
		
		case "price (low - high)":
			priceSort = PriceSort.PriceLowToHigh;
			break;
			
		case "price (high - low)":
			priceSort = PriceSort.PriceHighToLow;
			break;
			
		case "name (a - z)":
			priceSort = PriceSort.NameAZ;
			break;
			
		case "name (z - a)":
			priceSort = PriceSort.NameZA;
			break;
			
		default:
			new IllegalArgumentException("Incorrect price sort option provided");
		}
		
		value = priceSort.getOptionValue();
		util.setSelectOptionByValue(sortDropdown, value);
		
	}
	
	public List<Double> sortPrice(String sortOrder, List<Double> list) {
		
		String order = sortOrder.trim().toLowerCase();	
		
		if(order.equalsIgnoreCase("Ascending") || order.equalsIgnoreCase("Asc")) {
			
			Collections.sort(list);
		}
		else if(order.equalsIgnoreCase("Descending") || order.equalsIgnoreCase("Desc")){
			
			Collections.sort(list, Collections.reverseOrder());
		}
		
		return list;
	}
	
	public List<String> sortName(String sortOrder, List<String> list) {
		
		String order = sortOrder.trim().toLowerCase();

		if(order.equalsIgnoreCase("A to Z")) {
			
			Collections.sort(list);
		}
		else if(order.equalsIgnoreCase("Z to A")){
			
			Collections.sort(list, Collections.reverseOrder());
		}
		
		return list;
	}
	
	public Object getAllProduct_SpecificInfo(String priceOrNameOrUrl) {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Object listObj = null;
		int maxPage = getTotalPages();
		int pageNum =1;
		
		List<WebElement> products;
		List<Double> priceList = new ArrayList<Double>();
		List<String> nameList = new ArrayList<String>();
		List<String> URLList = new ArrayList<String>();

		do {

			if(priceOrNameOrUrl.trim().equalsIgnoreCase("Price")) {
				
				WebElement prodGrid = util.getElement(productGrid);
				products = prodGrid.findElements(By.xpath(".//a//span[@data-test='product-price']"));
				
				for(WebElement ele: products) {
					String price = ele.getText().trim();
					String numericPrice = price.replace("$", "");
					priceList.add(Double.parseDouble(numericPrice));
				}
								
				if(pageNum == maxPage) {
					listObj = priceList;
				}
				
			}
			else if(priceOrNameOrUrl.trim().equalsIgnoreCase("Url")) {
				WebElement prodGrid = util.getElement(productGrid);
				products = prodGrid.findElements(By.xpath("./a"));
				
				for(WebElement ele: products) {
					String URL = ele.getDomAttribute("href");
					URLList.add(URL);
				}
								
				if(pageNum == maxPage) {
					listObj = URLList;
				}
			}
			else {	
				
				WebElement prodGrid = util.getElement(productGrid);
				products = prodGrid.findElements(By.xpath(".//h5"));
				
				for(WebElement ele: products) {
					String name = ele.getText().trim();
					nameList.add(name);
				}
								
				if(pageNum == maxPage) {
					listObj = nameList;
				}
			}

			pageNum++;

			if(pageNum<=maxPage) {
				
				util.scrollIntoView(paginationBar);
				try {
					Thread.sleep(1000);
					if(util.isElementEnabled(nextPageBtn)) {
						util.doClick(nextPageBtn);
						Thread.sleep(3000);
						util.getPageLoadStatus();
						util.scrollToTopOfPage();
						Thread.sleep(1000);
					}
					System.out.println("Clicked on PageNumber = " + pageNum);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}while(pageNum <= maxPage);
		
		return listObj;
	}
	
	public List<String> getAllBrandNames() {
		
		@SuppressWarnings("unchecked")
		List<String> pageURLS = (List<String>) getAllProduct_SpecificInfo("Url");
		List<String> brandNameList = new ArrayList<String>();
		
		for (String endPoint: pageURLS) {
			driver.get(baseURL+endPoint);
			util.getPageLoadStatus();
			String brandNameText = util.getElementText(brandName).trim();
			brandNameList.add(brandNameText);
		}
		
		return brandNameList;
	}
	
	private int getTotalPages() {
		
		int pageCount=1;
		
		if(util.isElementDisplayed(paginationBar)) {
			int i = util.getElementSize(paginationBarBoxes);
			By maxPageNumber = By.xpath("(//ul[@class='pagination']/li)["+(i-1)+"]/a");
			
			pageCount = Integer.parseInt(util.getElementText(maxPageNumber));
		}		
		return pageCount;
	}
	
	public void enterTextInSearchBox(String text) {
		util.doSendKeys(searchBox, text);
		util.doClick(searchButton);
	}
	
	public Map <String,String> invalidSearchResult(String text) {
		enterTextInSearchBox(text);
		String searchCaption = util.getElementText(invalidSearchTextCaption);
		String searchResult = util.getElementText(invalidSearchTextResult);
		
		Map <String,String> invalidSearchTextResults = new HashMap<String,String>();
		invalidSearchTextResults.put("searchCaption", searchCaption);
		invalidSearchTextResults.put("searchResult", searchResult);
		
		return invalidSearchTextResults;

	}
	
	public void selectCheckbox(String checkboxName) {
		
		By checkbox = By.xpath("//label[normalize-space()='"+checkboxName+"']/input");
		util.doClick(checkbox);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void deselectBrandCheckbox(String checkboxName) {
		
		if(getPageURL().equals(baseURL+"/")) {
			driver.get(baseURL+"/");
			util.getPageLoadStatus();
		}
		
		try {
			Thread.sleep(5000);
			util.scrollToBottomOfPage();
			util.scrollToBottomOfPage();

			By checkbox = By.xpath("//label[normalize-space()='"+checkboxName+"']/input");
			
			boolean ischecckBoxSelected = util.getElement(checkbox).isSelected();
			System.out.println("Is "+checkboxName+" checkbox Selected? = " + ischecckBoxSelected);
			if(ischecckBoxSelected) {
				util.doClick(checkbox);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public Map<String, Object> viewProduct(String productName) {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		
		String productPriceText = util.getElementText(By.xpath("//h5[@class='card-title' and normalize-space()='"+productName+"']/../..//span[@data-test='product-price']"));
		String[] price = productPriceText.split("$");
		
		By productImage = By.xpath("//h5[@class='card-title' and normalize-space()='"+productName+"']/../..");
		util.doClick(productImage);
		
		obj.put("productPrice", Integer.parseInt((price)[1]));
		obj.put("pageObj", new ProductPage(driver));
		
		return obj;
	}
	
	
	
}
