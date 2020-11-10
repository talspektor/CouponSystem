package facade;

import java.util.ArrayList;
import java.util.List;

import DAO.CouponsDBDAO;
import beans.Company;
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
	
	/**
	 * @param companyId
	 * @throws CouponSystemException
	 * delete all company coupon and then delete the company
	 */ 
	public void deleteCoumpany(int companyId) throws CouponSystemException {
		couponsDAO.deleteCouponPurchaceByCompanyId(companyId);
		couponsDAO.deleteCouponsByCoumpanyId(companyId);
		companiesDAO.deleteCompany(companyId);
	}
	
	/**
	 * @return all companies from database
	 * @throws CouponSystemException
	 */
	public List<Company> getAllCompanies() throws CouponSystemException {
		return companiesDAO.getAllCompanies();
	}
	
	/**
	 * @param companyId
	 * @return
	 * @throws CouponSystemException
	 */
	public Company getCompanyById(int companyId) throws CouponSystemException {
		return companiesDAO.getOneCompany(companyId);
	}
}
