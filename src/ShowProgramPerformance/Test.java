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
		AdminFacade adminFacade = new AdminFacade(customerDAO, companiesDAO, couponsDAO);
		adminFacade.login("com.admin@admin", "admin");
		
		String companyName = "companyName99";
		String companyEmail = "companyEmail99";
		String companyPassword = "companyPassword99";
		Company company = new Company(companyName, companyEmail, companyPassword);
		
		adminFacade.addCompany(company);
		company.setEmail("companyEmail111");
		adminFacade.updateCompany(company);
		adminFacade.getCompanyById(company.getId());
		adminFacade.getAllCompanies();
		adminFacade.deleteCoumpany(company.getId());
		
		String customerFirstName = "ustomerFirstName88";
		String customerLastName = "customerLastName88";
		String customerEmail = "customerEmai88l";
		String customerPassword = "customerPassword88";
		Customer customer = new Customer(
				customerFirstName,
				customerLastName,
				customerEmail,
				customerPassword);
		adminFacade.addCustomer(customer);
		customer.setEmail("customer_new_email88");
		adminFacade.updateCustomer(customer);
		adminFacade.getCustomer(customer.getId());
		adminFacade.deleteCustomer(customer.getId());
		adminFacade.getAllCustomer();
		
		// Stop the daily job
		job.stop();
		// Close all connections
		ConnectionPool.getInstance().closeAllConnections();
	}
}
