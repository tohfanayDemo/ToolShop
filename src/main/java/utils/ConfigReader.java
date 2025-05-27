package utils;

public class ConfigReader {
	
	private static ThreadLocal<String> browserFromTestNG = new ThreadLocal<>();

	public static String getBrowserFromTestNG() {
		return browserFromTestNG.get();
	}

	public static void setBrowserFromTestNG(String browser) {
		browserFromTestNG.set(browser);
	}
	

}
