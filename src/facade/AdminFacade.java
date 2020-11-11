package facade;

import java.util.List;

import DAO.CompaniesDBDAO;
import beans.Company;
import beans.Customer;
import excetion.CouponSystemException;

public class AdminFacade extends ClienFacade {
	
	public AdminFacade() throws CouponSystemException {
		companiesDAO = new CompaniesDBDAO();
	}
	
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
		else {
			throw new CouponSystemException("coumpany with email=" + company.getEmail() + " and password=" + company.getPassword() + " already in database.");
		}
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
	 * @return the company with that id
	 * @throws CouponSystemException
	 */
	public Company getCompanyById(int companyId) throws CouponSystemException {
		return companiesDAO.getOneCompany(companyId);
	}
	
	/**
	 * @param customer
	 * @throws CouponSystemException
	 * add customer to database if email is unique
	 */
	public void addCustomer(Customer customer) throws CouponSystemException {
		if(!customerDAO.isEmailExisted(customer.getEmail())) {
			customerDAO.addCustomer(customer);
		} else {
			throw new CouponSystemException("you can't add customes, email must be unique");
		}
	}
	
	/**
	 * @param customer
	 * @throws CouponSystemException
	 * update customer in database 
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		customerDAO.updateCustomer(customer);
	}
	
	/**
	 * @param customerId
	 * @throws CouponSystemException
	 * delete customer coupon purchaces and delete costomer from database
	 */
	public void deleteCustomer(int customerId) throws CouponSystemException {
		couponsDAO.deleteCouponPurchaceByCustomerId(customerId);
		customerDAO.deleteCustomer(customerId);
	}
	
	/**
	 * @return list of all the customers from database
	 * @throws CouponSystemException
	 */
	public List<Customer> getAllCustomer() throws CouponSystemException {
		return customerDAO.getAllCustomers();
	}
	
	/**
	 * @param customerId
	 * @return the customer with the customer id from database
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(int customerId) throws CouponSystemException {
		return customerDAO.getOneCustomer(customerId);
	}
}
