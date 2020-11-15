package facade;

import java.util.Date;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import beans.Coupon;
import beans.Customer;
import excetion.CouponSystemException;

public class CustomerFacade extends ClienFacade {
	
	private int id;
	
	public CustomerFacade(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) {
		super(customerDAO, companiesDAO, couponsDAO);
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
			System.out.println("customer fail to login");
			return false;
		}
		System.out.println("login success");
		this.id = customer.getId();
		return true;
	}
	
	public void purchaseCoupon(int couponId) throws CouponSystemException {
		//TODO: 
		// if end date > current date, 
		//validate coupon not purchase before
		if(customerDAO.isCouponAlreadyPurchased(id, couponId)) { 
			System.out.println("customer already purchase this coupon");
			return;
		}
		// validate coupon is available
		Coupon coupon = couponsDAO.getOneCoupon(couponId);
		if(!(coupon.getAmount()>0)) { 
			System.out.println("coupon amout is not available");
			return;
		}
		// validate coupon experation
		if (coupon.getEndDate().compareTo(new Date()) < 0) {
			System.out.println("coupon expierd");
			return;
		}
		couponsDAO.addCouponPurchase(id, couponId);
		int amount = coupon.getAmount();
		coupon.setAmount(amount--);
		couponsDAO.updateCoupon(coupon);
	}

}
