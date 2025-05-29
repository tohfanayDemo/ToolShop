package constants;

import java.util.Arrays;
import java.util.List;

public class Constants {

	/********************* HOME PAGE **********************/
	public static final String EXPECTED_URL_VALUE_HOMEPAGE = "https://practicesoftwaretesting.com/"; 
	public static final List<String> EXPECTED_CATEGORY_NAMES = Arrays.asList("Hand Tools, Power Tools, Other, Special Tools, Rentals"); 
	public static final List<String> EXPECTED_LANGUAGE_OPTIONS = Arrays.asList("DE, EN, ES, FR, NL, TR"); 

	/********************* CONTACT PAGE **********************/
	public static final String PARTIAL_URL_VALUE_CONTACTPAGE = "contact"; 
	
	/********************* REGISTRATION PAGE **********************/
	public static final String PARTIAL_URL_VALUE_REGISTRATIONPAGE = "auth/register"; 
	
	/********************* LOGIN PAGE **********************/
	public static final String PARTIAL_URL_VALUE_LOGINPAGE = "auth/login"; 
	public static final String HEADER_VALUE_LOGINPAGE = "Login";
	
	/********************* MY ACCOUNT PAGE **********************/
	public static final String HEADER_VALUE_MYACCOUNTPAGE = "My account"; 

	/********************* CART PAGE **********************/
	public static final List<String> COLUMN_HEADERS_CARTPAGE = Arrays.asList("Item","Quantity","Price","Total"); 
	public static final String PAYMENT_SUCCESSFUL_TEXT = "Payment was successful"; 
	
	
	/********************* FRAMEWORK CONSTANTS **********************/
	
	public static final String EXCEL_FILE_PATH = "\\src\\test\\resources\\testData\\testdata.xlsx";
	public static final String REGISTRATION_EXCEL_SHEET_NAME = "Registration";
    public static final String LOGIN_EXCEL_SHEET_NAME = "Login";
}
