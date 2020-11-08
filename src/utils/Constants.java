package utils;


public enum Constants {
	DB_NAME("coupons_system"),
	COUPONS_TABLE("coupons"),
	COMPANIES_TABLE("compnies"),
	CATEGORIES_TABLE("categories"),
	CUSTOMERS_TABLE("customers"),
	CUSTOMERS_VS_COUPONS("customers_vs_coupons"),
	ID("id"),
	EMAIL("email"),
	PAAWORD("password"),
	NAME("name"),
	FIRST_NAME("first_name"),
	LAST_NAME("last_name"),
	CUSTOMER_ID("customer_id"),
	COUPON_ID("coupon_id"),
	COMPANY_ID("company_id"),
	CATEGORY_ID("category_id"),
	TITLE("title"),
	DESCRIPTION("description"),
	START_DATE("start_date"),
	END_DATE("end_date"),
	AMOUNT("amount"),
	PRICE("price"),
	IMAGE("image");
	
	private String value;
	
	Constants(String value) {
		this.value = value;
	}
	
	String getValue() {
		return this.value;
	}
}
