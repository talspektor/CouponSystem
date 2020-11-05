package utils;

public enum Constants {
	DB_NAME("coupons_system"),
	COUPONS_TABLE("coupons"),
	COMPANIES_TABLE("compnies"),
	CATEGORIES_TABLE("categories"),
	CUSTOMERS_TABLE("costomers"),
	EMAIL("email"),
	PAAWORD("password");
	
	private String value;
	
	Constants(String value) {
		this.value = value;
	}
	
	String getValue() {
		return this.value;
	}
}
