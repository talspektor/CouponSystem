package facade;

import java.util.ArrayList;
import java.util.Date;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import beans.Category;
import beans.Coupon;
import beans.Customer;
import excetion.CouponSystemException;

public class CustomerFacade extends ClienFacade {
	
	private int id;
	
	public CustomerFacade(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) {
		super(customerDAO, companiesDAO, couponsDAO);
	}

	public int getId() {
		return id;
	}

	/**
	 *@param email
	 *@param password
	 *@return true is found customer with given email and password in database
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		Customer customer = customerDAO.getCustomerByEmailAndPassword(email, password);
		if(customer == null) { 
			System.out.println("customer fail to login :(");
			return false;
		}
		System.out.println("login success :)");
		this.id = customer.getId();
		return true;
	}
	
	/**
	 * @param couponId
	 * @throws CouponSystemException
	 * purchase coupon if valid for customer
	 */
	public void purchaseCoupon(int couponId) throws CouponSystemException {
		System.out.println("Customer purchaseCoupon");
		//validate coupon not purchase before
		if (!validateCoupnNotPurchaseAlready(couponId)) { return; }
		// validate coupon is available
		Coupon coupon = couponsDAO.getOneCoupon(couponId);
		if (!validateCouponAvailable(coupon)) { return; }
		// validate coupon expiration
		if (!validateCouponExpiration(coupon)) { return; }
		couponsDAO.addCouponPurchase(id, couponId);
		int amount = coupon.getAmount();
		coupon.setAmount(amount--);
		couponsDAO.updateCoupon(coupon);
	}
	
	/**
	 * @return all customer coupons
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getCoupons() throws CouponSystemException {
		System.out.println("Customer getCoupons");
		return couponsDAO.getAllCustomerCoupons(id);
	}
	
	/**
	 * @param category
	 * @return all customer coupon for given category
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getCoupons(Category category) throws CouponSystemException {
		System.out.println("Customer getCoupons(Category)");
		return couponsDAO.getAllCustomerCouponsForCategoty(category.getId(), id);
	}
	
	public ArrayList<Coupon> getCoupons(double maxPrice) throws CouponSystemException {
		System.out.println("Customer getCoupons(double)");
		return couponsDAO.getAllCompanyCouponsMaxPrice(maxPrice, id);
	}
	
	/**
	 * @return the customer that login
	 * @throws CouponSystemException
	 */
	public Customer getCustomerDetails() throws CouponSystemException {
		System.out.println("Customer getCustomerDetails()");
		return customerDAO.getOneCustomer(id);
	}
	
	// ---------COUPON VALIDATION --------------
	
	/**
	 * @param couponId
	 * @return true if coupon isn't already purchase
	 * @throws CouponSystemException
	 */
	private boolean validateCoupnNotPurchaseAlready(int couponId) throws CouponSystemException {
		if(!customerDAO.isCouponAlreadyPurchased(id, couponId)) { return true; }
		System.out.println("customer already purchase this coupon");
		return false;
	}
	
	
	/**
	 * @param coupon
	 * @return true if coupon is available
	 */
	private boolean validateCouponAvailable(Coupon coupon) {
		if(coupon.getAmount()>0) { return true; }
		System.out.println("coupon is not available");
		return false;
	}
	
	/**
	 * @param coupon
	 * @return true if coupon is not expired
	 */
	private boolean validateCouponExpiration(Coupon coupon) {
		if (coupon.getEndDate().compareTo(new Date()) > 0) { return true; }
		System.out.println("coupon expierd");
		return false;
	}

}
