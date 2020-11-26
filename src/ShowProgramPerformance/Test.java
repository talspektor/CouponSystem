package ShowProgramPerformance;


import DAO.CompaniesDAO;
import DAO.CompaniesDBDAO;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import DAO.CustomesDAO;
import connection.ConnectionPool;
import excetion.CouponSystemException;
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
		TestAdmin testAdmin = new TestAdmin(customerDAO, companiesDAO, couponsDAO);
		testAdmin.test();
		// Company
		TestCompamy testCompamy = new TestCompamy(customerDAO, companiesDAO, couponsDAO);
		testCompamy.test();
		// Customer
		TestCustomer testCustomer = new TestCustomer(customerDAO, companiesDAO, couponsDAO);
		testCustomer.test();
		
		cleenClose(job);	
	}
	
	private void cleenClose(DailyJob job) throws CouponSystemException {
		// Stop the daily job
		job.stop();
		// Close all connections
		ConnectionPool.getInstance().closeAllConnections();
	}
}
