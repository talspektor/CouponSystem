package ShowProgramPerformance;

import java.sql.Date;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import beans.Category;
import beans.Coupon;
import excetion.CouponSystemException;
import facade.CompanyFacade;

public class TestCompamy {

	private CustomesDAO customerDAO;
	private CompaniesDAO companiesDAO;
	private CouponsDAO couponsDAO;
	
	public TestCompamy(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) {
		super();
		this.customerDAO = customerDAO;
		this.companiesDAO = companiesDAO;
		this.couponsDAO = couponsDAO;
	}
	
	/** test all company client facade methods
	 * @param customerDAO
	 * @param companiesDAO
	 * @param couponsDAO
	 * @throws CouponSystemException
	 */
	public void test() throws CouponSystemException {
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
}
