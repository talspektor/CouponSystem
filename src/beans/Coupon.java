package beans;

import java.sql.Date;

/**
 * @author taltalspektor
 *
 */
public class Coupon {

	private int id;
	private int companyId;
	private int categoryId;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int amount;
	private double price;
	private String imageUrl;
	
	public Coupon(int companyId, int categoryId, String title, String description, Date startDate, Date endDate,
			int amount, double price, String imageUrl) {
		super();
		this.companyId = companyId;
		this.categoryId = categoryId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public Coupon(int id, int companyId, int categoryId, String title, String description, Date startDate,
			Date endDate, int amount, double price, String imageUrl) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.categoryId = categoryId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyId=" + companyId + ", categoryId=" + categoryId + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", imageUrl=" + imageUrl + "]";
	}
	
	
}
