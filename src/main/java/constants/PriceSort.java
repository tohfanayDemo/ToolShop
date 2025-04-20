package constants;

public enum PriceSort {
	
	NameAZ("name,asc", "Name (A - Z)", 1), NameZA("name,desc", "Name (Z - A)", 2),
	PriceHighToLow("price,desc", "Price (High - Low)", 3), PriceLowToHigh("price,asc", "Price (Low - High)", 4);
	
	private String optionValue;
	private String optionText;
	private int optionIndex;
	
	PriceSort(String optionValue, String optionText, int optionIndex){
		this.optionValue = optionValue;
		this.optionText = optionText;
		this.optionIndex = optionIndex;
	}
	
	public String getOptionValue() {
		return optionValue;
	}
	
	public String getOptionText() {
		return optionText;
	}
	
	public int getOptionIndex() {
		return optionIndex;
	}
}
