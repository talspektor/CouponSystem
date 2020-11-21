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
	@Override
	public boolean login(String email, String password) {
		System.out.println("Admin login");
		if (email == "com.admin@admin" && password == "admin") {
			System.out.println("login success :)");
			return true;
		}
		System.out.println("login fail :(");
		return false;
	}
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * add company to database not allow email and password duplication
	 */
	public void addCompany(Company company) throws CouponSystemException {
		System.out.println("Admin addCompany");
		if (companiesDAO.isCompnyEmailUnique(company.getEmail())&&
				companiesDAO.isCompanyPasswordUnique(company.getPassword()))
		companiesDAO.addCompany(company);
		else {
			System.out.println("addCompany fail: coumpany with"
					+ " email=" + company.getEmail()
					+ " or password=" + company.getPassword() + " is not unique.");
			System.out.println("coumpany with email=" + company.getEmail()
			+ " and password=" + company.getPassword()
			+ " already in database.");
		}
	}
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * update company in database (don't change company name) 
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		System.out.println("Admin updateCompany");
		Company dbCompany = getCompany(company.getId());
		if (dbCompany == null) {
			System.out.println("Admin compant not found");
			return;
		} 
		dbCompany.setEmail(company.getEmail());
		dbCompany.setPassword(company.getPassword());
		companiesDAO.updateCompany(dbCompany);
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
	public Company getCompany(int companyId) throws CouponSystemException {
		System.out.println("Admin getCompany");
		return companiesDAO.getOneCompany(companyId);
	}
	
	/**
	 * @param customer
	 * @throws CouponSystemException
	 * add customer to database if email is unique
	 */
	public void addCustomer(Customer customer) throws CouponSystemException {
		System.out.println("Admin addCustomer");
		if(!customerDAO.isEmailExists(customer.getEmail())) {
			customerDAO.addCustomer(customer);
		} else {
			System.out.println("you can't add customes, email must be unique");
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
	 * delete customer coupon purchases and delete customer from database
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
