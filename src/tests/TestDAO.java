package tests;


import DAO.CompaniesDAO;
import DAO.CompaniesDBDAO;
import DAO.CustomersDBDAO;
import beans.Company;
import beans.Customer;
import excetion.CouponSystemException;

public class TestDAO {

	public static void main(String[] args) {
		
		try {
			// ----- TEST CustomerDBDAO ------------
//			CustomersDBDAO custometDAO = new CustomersDBDAO();
//			String email = "tal@spektor99";
//			String password = "password";
//			Customer customer =  new Customer("ethan", "spektor", email, password);
//			custometDAO.addCustomer(customer);
//			System.out.println(custometDAO.isCustomerExists(email, password)); 
//			custometDAO.updateCustomer(customer);
//			custometDAO.deleteCustomer(2);
//			System.out.println(custometDAO.getAllCustomers());
//			System.out.println(custometDAO.getOneCustomer(3));
			
			// -----TEST CompaniesDBDAO ---------
			CompaniesDAO companiesDAO = new CompaniesDBDAO();
			String name = "company4";
			String email = "email4";
			String password = "passwordNew";
			Company company = new Company(name, email, password);
			companiesDAO.addCompany(company);
//			System.out.println(companiesDAO.isCompnyExists(email, password)); 
//			companiesDAO.updateCompany(company);
//			companiesDAO.deleteCompany(1);
//			System.out.println(companiesDAO.getAllCompanies());
//			System.out.println(companiesDAO.getOneCompany(2));
					
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
