package ShowProgramPerformance;

import java.sql.Date;

import DAO.CompaniesDAO;
import DAO.CompaniesDBDAO;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import DAO.CustomesDAO;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import connection.ConnectionPool;
import excetion.CouponSystemException;
import facade.AdminFacade;
import facade.CompanyFacade;
import job.DailyJob;

public class Test {
	
	private CustomesDAO customerDAO;
	private CompaniesDAO companiesDAO;
	private CouponsDAO couponsDAO;

	public void testAll() throws CouponSystemException {
		// Start the daily job
		DailyJob job = new DailyJob(new CouponsDBDAO());
		Thread thread = new Thread(job);
		thread.start();
		//**********************
		// Create the DAO's
		CustomesDAO customerDAO = new CustomersDBDAO();
		CompaniesDAO companiesDAO = new CompaniesDBDAO();
		CouponsDAO couponsDAO = new CouponsDBDAO();
		// Clients:
		// Administrator
		testAdminFacade(customerDAO, companiesDAO, couponsDAO);
		// Company
		testCompanyFacade(customerDAO, companiesDAO, couponsDAO);
		
		cleenClose(job);
		
		
	}

	/** test all administrator client facade methods
	 * @param customerDAO
	 * @param companiesDAO
	 * @param couponsDAO
	 * @throws CouponSystemException
	 */
	private void testAdminFacade(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) throws CouponSystemException {
		System.out.println("Administarator test\n========================\n");
		AdminFacade adminFacade = new AdminFacade(customerDAO, companiesDAO, couponsDAO);
		adminFacade.login("com.admin@admin", "admin");

		String companyName = "companyName99";
		String companyEmail = "companyEmail99";
		String companyPassword = "companyPassword99";
		Company company = new Company(companyName, companyEmail, companyPassword);

		adminFacade.addCompany(company);
		company.setEmail("companyEmail111");
		adminFacade.updateCompany(company);
		adminFacade.getCompany(company.getId());
		adminFacade.getAllCompanies();
		adminFacade.deleteCoumpany(20);

		System.out.println("===========================");
		//
		String customerFirstName = "ustomerFirstName88";
		String customerLastName = "customerLastName88";
		String customerEmail = "customerEmai88l";
		String customerPassword = "customerPassword88";
		Customer customer = new Customer(customerFirstName, customerLastName, customerEmail, customerPassword);
		adminFacade.addCustomer(customer);
		customer.setEmail("customer_new_email88");
		adminFacade.updateCustomer(customer);
		adminFacade.getCustomer(5);
		adminFacade.deleteCustomer(customer.getId());
		adminFacade.getAllCustomer();

		System.out.println("==========================");
	}
	
	/** test all company client facade methods
	 * @param customerDAO
	 * @param companiesDAO
	 * @param couponsDAO
	 * @throws CouponSystemException
	 */
	private void testCompanyFacade(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) throws CouponSystemException {
		System.out.println("Company test\n==========================\n");
		CompanyFacade companyFacade = new CompanyFacade(customerDAO, companiesDAO, couponsDAO);
		companyFacade.login("email", "password");
		
		Coupon coupon = new Coupon(1, 1, "some_title", "some description", new Date(2020, 10, 10), new Date(2021, 10, 10), 5, 20.5, "someUrl");
		companyFacade.addCoupon(coupon);
		coupon = couponsDAO.getOneCoupon(companyFacade.getCompanyId());
		coupon.setPrice(100.5);
		companyFacade.updateCoupon(coupon);
		companyFacade.deleteCoupon(1);
		companyFacade.getCompanyCoupons();
		companyFacade.getCompanyCoupons(Category.FOOD);
		companyFacade.getCompanyCoupons(99);
		companyFacade.getCompanyDetails();
		
		System.out.println("==========================");
	}
	
	private void testCustomerFacade(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) throws CouponSystemException {
		System.out.println("Customer test\n=========================\n");
		
	}
	
	
	private void cleenClose(DailyJob job) throws CouponSystemException {
		// Stop the daily job
		job.stop();
		// Close all connections
		ConnectionPool.getInstance().closeAllConnections();
	}
}
