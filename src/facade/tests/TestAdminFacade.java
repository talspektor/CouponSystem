package facade.tests;

import beans.Company;
import excetion.CouponSystemException;
import facade.AdminFacade;

public class TestAdminFacade {

	public static void main(String[] args) {
		
		try {
			AdminFacade facade = new AdminFacade();
			String name = "name";
			String email = "com.admin@admin";
			String password = "admin";
			Company company = new Company(name, email, password);
//			facade.addCompany(company);
			company.setEmail("new__email");
			
			facade.updateCompany(company);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
}
