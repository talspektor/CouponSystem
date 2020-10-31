package DAO;

import java.util.List;

/**
 * @author taltalspektor
 *
 */
public class Company {

	private int id;
	private String name;
	private String emaill;
	private String password;
	private List<Coupon> coupons;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmaill() {
		return emaill;
	}
	
	public void setEmaill(String emaill) {
		this.emaill = emaill;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Coupon> getCoupons() {
		return coupons;
	}
	
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", emaill=" + emaill + ", password=" + password + ", coupons="
				+ coupons + "]";
	}
	
	
	
}
