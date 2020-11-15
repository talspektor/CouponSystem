package facade;

import java.util.ArrayList;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import beans.Category;
import beans.Company;
import beans.Coupon;
import excetion.CouponSystemException;

/**
 * @author tals
 *
 */
public class CompanyFacade extends ClienFacade {
	
	private int companyId;
	
	public CompanyFacade(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) {
		super(customerDAO, companiesDAO, couponsDAO);
	}
	
	public int getCompanyId() {
		return companyId;
	}

	/**
	 * @param email, password
	 * @return true is company is in database
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		System.out.println("CompanyFacade login");
		if (companiesDAO.isCompnyExists(email, password)) {
			Company company = companiesDAO.getCompany(email, password);
			this.companyId = company.getId();
			System.out.println("login success :)");
			return true;
		}
		System.out.println("fail to login: there is no company with email=" + email
				+ " and password=" + password + " in database.");
		return false;
	}
	
	/**
	 * @param coupon
	 * @throws CouponSystemException
	 * add new coupon of the company to database
	 */
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		System.out.println("CompanyFacade addCoupon");
		if (!couponsDAO.isCouponExists(coupon.getTitle(), companyId)) {
			couponsDAO.addCoupon(coupon);
		}
		System.out.println("coupon with title=" + coupon.getTitle()
		+ " and company-id=" + companyId
		+ " is in database. you can't add two coupons with the same title and company id");
	}
	
	/**
	 * @param coupon
	 * @throws CouponSystemException
	 * update coupon in database
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		System.out.println("CompanyFacade updateCoupon");
		couponsDAO.updateCoupon(coupon);
	}
	
	/**
	 * @param couponId
	 * @throws CouponSystemException
	 * delete coupon from database
	 */
	public void deleteCoupon(int couponId) throws CouponSystemException {
		System.out.println("CompanyFacade deleteCoupon");
		couponsDAO.deleteCoutonPurchaceByCouponId(couponId);
		couponsDAO.deleteCoupon(couponId);
	}
	
	/**
	 * @return list of all company coupons
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getCompanyCoupons() throws CouponSystemException{
		System.out.println("CompanyFaceade getCompanyCoupons");
		return couponsDAO.getAllCompanyCoupons(companyId);
	}
	
	/**
	 * @param category
	 * @return list of all company coupons for a single category
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getCompanyCoupons(Category category) throws CouponSystemException {
		System.out.println("CompanyFaceade getCompanyCoupons");
		int categoryId = category.getValue();
		return couponsDAO.getAllCompanyCouponsByCategory(categoryId, companyId);
	}
	
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException {
		System.out.println("CompanyFaceade getCompanyCoupons");
		return couponsDAO.getAllCompanyCouponsMaxPrice(maxPrice, companyId);
	}
	
	public Company getCompanyDetails() throws CouponSystemException {
		System.out.println("CompanyFaceade getCompanyDetails");
		return companiesDAO.getOneCompany(companyId);
	}
}
