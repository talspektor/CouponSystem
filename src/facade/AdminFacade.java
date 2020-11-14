package facade;

import java.util.List;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import beans.Company;
import beans.Customer;
import excetion.CouponSystemException;

public class AdminFacade extends ClienFacade {
	
	public AdminFacade(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) {
		super(customerDAO, companiesDAO, couponsDAO);
	}

	/**
	 * @param email
	 * @param password
	 * @return return true if values are currect
	 */
	public boolean login(String email,
			String password) {
		System.out.println("Admin login");
		return email == "com.admin@admin" && password == "admin";
	}
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * add company to database not allow email and password duplication
	 */
	public void addCompany(Company company) throws CouponSystemException {
		System.out.println("Admin addCompany");
		if (!companiesDAO.isCompnyExists(company.getEmail(), company.getPassword()))
		companiesDAO.addCompany(company);
		else {
			System.out.println("coumpany with email=" + company.getEmail()
			+ " and password=" + company.getPassword()
			+ " already in database.");
		}
	}
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * update company in database
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		System.out.println("Admin updateCompany");
		companiesDAO.updateCompany(company);
	}
	
	/**
	 * @param companyId
	 * @throws CouponSystemException
	 * delete all company coupon and then delete the company
	 */ 
	public void deleteCoumpany(int companyId) throws CouponSystemException {
		System.out.println("Admin deleteCompany");
		couponsDAO.deleteCouponPurchaceByCompanyId(companyId);
		couponsDAO.deleteCouponsByCoumpanyId(companyId);
		companiesDAO.deleteCompany(companyId);
	}
	
	/**
	 * @return all companies from database
	 * @throws CouponSystemException
	 */
	public List<Company> getAllCompanies() throws CouponSystemException {
		System.out.println("Admin getAllCompanies");
		return companiesDAO.getAllCompanies();
	}
	
	/**
	 * @param companyId
	 * @return the company with that id
	 * @throws CouponSystemException
	 */
	public Company getCompanyById(int companyId) throws CouponSystemException {
		System.out.println("Admin getCompanyById");
		return companiesDAO.getOneCompany(companyId);
	}
	
	/**
	 * @param customer
	 * @throws CouponSystemException
	 * add customer to database if email is unique
	 */
	public void addCustomer(Customer customer) throws CouponSystemException {
		System.out.println("Admin addCustomer");
		if(!customerDAO.isCustomerExists(customer.getEmail())) {
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
		System.out.println("Admin updateCustomer");
		customerDAO.updateCustomer(customer);
	}
	
	/**
	 * @param customerId
	 * @throws CouponSystemException
	 * delete customer coupon purchaces and delete costomer from database
	 */
	public void deleteCustomer(int customerId) throws CouponSystemException {
		System.out.println("Admin deleteCustomer");
		couponsDAO.deleteCouponPurchaceByCustomerId(customerId);
		customerDAO.deleteCustomer(customerId);
	}
	
	/**
	 * @return list of all the customers from database
	 * @throws CouponSystemException
	 */
	public List<Customer> getAllCustomer() throws CouponSystemException {
		System.out.println("Admin getAllCustomer");
		return customerDAO.getAllCustomers();
	}
	
	/**
	 * @param customerId
	 * @return the customer with the customer id from database
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(int customerId) throws CouponSystemException {
		System.out.println("Admin getCustomer");
		return customerDAO.getOneCustomer(customerId);
	}
}
