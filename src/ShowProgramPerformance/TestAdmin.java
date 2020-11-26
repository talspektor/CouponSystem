package ShowProgramPerformance;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import beans.Company;
import beans.Customer;
import excetion.CouponSystemException;
import facade.AdminFacade;

public class TestAdmin {
	
	private CustomesDAO customerDAO;
	private CompaniesDAO companiesDAO;
	private CouponsDAO couponsDAO;

	public TestAdmin(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) {
		super();
		this.customerDAO = customerDAO;
		this.companiesDAO = companiesDAO;
		this.couponsDAO = couponsDAO;
	}

	/** test all administrator client facade methods
	 * @param customerDAO
	 * @param companiesDAO
	 * @param couponsDAO
	 * @throws CouponSystemException
	 */
	public void test() throws CouponSystemException {
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
}
