package DAO.tests;


import java.sql.Date;

import DAO.CompaniesDAO;
import DAO.CompaniesDBDAO;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import DAO.CustomesDAO;
import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import excetion.CouponSystemException;

public class TestDAO {

	public static void main(String[] args) {
		
		try {
			// ----- TEST CustomerDBDAO ------------
			CustomesDAO custometDAO = new CustomersDBDAO();
			String email = "tal@spektor_";
			String password = "password_";
			String firstName = "firstName_";
			String lastName = "lastName_";
			Customer customer = new Customer();
			for (int i = 0; i < 20; i++) {
				email = "tal@spektor_" + i;
				password = "password_" + i;
				firstName = "firstName_" + i;
				lastName = "lastName_" + i;
				customer = new Customer(firstName, lastName, email, password);
				custometDAO.addCustomer(customer);
				
			}
			System.out.println(custometDAO.isCustomerExists(email, password));
			customer.setEmail("new_email");
			custometDAO.updateCustomer(customer);
			custometDAO.deleteCustomer(2);
			System.out.println(custometDAO.getAllCustomers());
			System.out.println(custometDAO.getOneCustomer(3));
			
			// -----TEST CompaniesDBDAO ---------
			CompaniesDAO companiesDAO = new CompaniesDBDAO();
			String companyName = "company_";
			String companyEmail = "email_";
			String companyPassword = "password_";
			Company company = new Company();
			for (int i = 0; i < 10; i++) {
				companyName = "company_" + i;
				companyEmail = "email_" + i;
				companyPassword = "password_" + i;
				company = new Company(companyName, companyEmail, companyPassword);
				companiesDAO.addCompany(company);
			}
			System.out.println(companiesDAO.isCompnyExists(email, password));
			company.setEmail("new_email");
			companiesDAO.updateCompany(company);
			companiesDAO.deleteCompany(2);
			System.out.println(companiesDAO.getAllCompanies());
			System.out.println(companiesDAO.getOneCompany(3));
			
			// ----- TEST CouponsDBDAO -----
			CouponsDAO couponsDAO = new CouponsDBDAO();
			int companyId = 2;
			int categoryId = 1;
			String title = "newtitle";
			String description = "dxfxadzc";
			Date startDate = new Date(2020, 2, 3);
			Date endDate = new Date(1980, 2, 21);
			int amount = 2;
			double price = 5.5;
			String imageUrl = "asdgadfg";
			Coupon coupon = new Coupon(companyId, categoryId, title, description, startDate, endDate, amount, price, imageUrl);
			couponsDAO.addCoupon(coupon);
			coupon.setId(4);
			couponsDAO.updateCoupon(coupon);
			couponsDAO.deleteCoupon(coupon.getId());
			System.out.println(couponsDAO.getAllCoupons());
			System.out.println(couponsDAO.getOneCoupon(5));
			couponsDAO.addCouponPurchase(1, 5);
			couponsDAO.deleteCouponPurchase(1, 5);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
