package ShowProgramPerformance;

import DAO.CompaniesDAO;
import DAO.CompaniesDBDAO;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import DAO.CustomesDAO;
import beans.Company;
import beans.Customer;
import connection.ConnectionPool;
import excetion.CouponSystemException;
import facade.AdminFacade;
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
		// Administrator
		System.out.println("Administarator test");
		System.out.println("===========================");
		System.out.println();
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
	
	private void testCompanyFacade(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) {
		
	}
	
	private void cleenClose(DailyJob job) throws CouponSystemException {
		// Stop the daily job
		job.stop();
		// Close all connections
		ConnectionPool.getInstance().closeAllConnections();
	}
}
