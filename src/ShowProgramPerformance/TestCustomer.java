package ShowProgramPerformance;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import beans.Category;
import beans.Customer;
import excetion.CouponSystemException;
import facade.CustomerFacade;

public class TestCustomer {

	private CustomesDAO customerDAO;
	private CompaniesDAO companiesDAO;
	private CouponsDAO couponsDAO;
	
	public TestCustomer(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) {
		super();
		this.customerDAO = customerDAO;
		this.companiesDAO = companiesDAO;
		this.couponsDAO = couponsDAO;
	}
	
	public void test() throws CouponSystemException {
		System.out.println("Customer test\n=========================\n");
		CustomerFacade customerFacade = new CustomerFacade(customerDAO, companiesDAO, couponsDAO);
		customerFacade.login("email1", "password1");
		
		Customer customer = new Customer("firstName3", "lastName3", "email3", "password3");
		customerFacade.purchaseCoupon(customerFacade.getId());
		customerFacade.getCoupons();
		customerFacade.getCoupons(Category.FOOD);
		customerFacade.getCoupons(99);
		customerFacade.getCustomerDetails();
		
		System.out.println("==========================");
	}
}
