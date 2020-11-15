package facade.tests;

import java.sql.Date;

import DAO.CompaniesDBDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import beans.Coupon;
import excetion.CouponSystemException;
import facade.CompanyFacade;

public class TestCompanyFacade {
	
	public static void main(String[] args) {
		
		try {
			CompanyFacade facade = new CompanyFacade(new CustomersDBDAO(), new CompaniesDBDAO(), new CouponsDBDAO());
			facade.login("email2", "password2");
			int categoryId = 1;
			String title = "title1";
			String description = "description";
			Date startDate = new Date(2020, 1, 1);
			Date endDate = new Date(2020, 2, 2);
			int amount = 3;
			double price = 5.4;
			String imageUrl = "image";
			Coupon coupon = new Coupon(facade.getCompanyId(),
					categoryId,
					title,
					description,
					startDate,
					endDate,
					amount,
					price,
					imageUrl);
			facade.addCoupon(coupon);
			
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
