package ShowProgramPerformance;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import beans.Category;
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
		
		customerFacade.purchaseCoupon(customerFacade.getId());
		System.out.println(customerFacade.getCoupons());
		System.out.println(customerFacade.getCoupons(Category.FOOD));
		System.out.println(customerFacade.getCoupons(120));
		System.out.println(customerFacade.getCustomerDetails());
		
		System.out.println("==========================");
	}
}
