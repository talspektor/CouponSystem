package facade;

import java.util.ArrayList;
import java.util.List;

import beans.Company;
import beans.Coupon;
import excetion.CouponSystemException;

public class AdminFacade extends ClienFacade {
	
	/**
	 * @param email
	 * @param password
	 * @return return true if values are currect
	 */
	public boolean login(String email,
			String password) {
		return email == "com.admin@admin" && password == "admin";
	}
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * add company to database not allow email and password duplication
	 */
	public void addCompany(Company company) throws CouponSystemException {
		if (!companiesDAO.isCompnyExists(company.getEmail(), company.getPassword()))
		companiesDAO.addCompany(company);
	}
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * update company in database
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		companiesDAO.updateCompany(company);
	}
	
	public void deleteCoumpany(Company company) throws CouponSystemException {
		
		couponsDAO.deleteCouponsByCoumpanyId(company.getId());
		couponsDAO.deleteCouponPurchase(customeId, couponId);
	}
}
