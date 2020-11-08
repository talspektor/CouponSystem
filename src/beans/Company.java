package beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author taltalspektor
 *
 */
public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6036277199915661257L;
	private int id;
	private String name;
	private String email;
	private String password;
	private List<Coupon> coupons;
	
	
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", coupons="
				+ coupons + "]";
	}
	
	
	
}
