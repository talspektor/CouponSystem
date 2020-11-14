package facade.tests;

import DAO.CompaniesDBDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import beans.Company;
import beans.Customer;
import excetion.CouponSystemException;
import facade.AdminFacade;

public class TestAdminFacade {

	public static void main(String[] args) {
		
		try {
			AdminFacade facade = new AdminFacade(new CustomersDBDAO(),
					new CompaniesDBDAO(),
					new CouponsDBDAO());
			
			String name = "name";
			String email = "com.admin@admin1";
			String password = "admin";
			Company company = new Company(name, email, password);
//			facade.addCompany(company);
			
			CompaniesDBDAO dbdao = new CompaniesDBDAO();
//			company = facade.getCompanyById(33);
//			company.setEmail("new__email33");
//			facade.updateCompany(company);
//			facade.deleteCoumpany(33);
			System.out.println(facade.getCompanyById(30));
			
			String firstName = "firstName";
			String lastName = "lastName";
			String customerEmail = "emailFacade";
			String customerrPassword = "password";
			Customer customer = new Customer(firstName, lastName, customerEmail, customerrPassword);
			
//			facade.addCustomer(customer);
			System.out.println(facade.getCustomer(106));
			customer = facade.getCustomer(106);
			customer.setFirstName("new_name");
			facade.updateCustomer(customer);
			System.out.println(facade.getCustomer(106));
			facade.deleteCustomer(105);
			facade.getAllCustomer();
			
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
}
