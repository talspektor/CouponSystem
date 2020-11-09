package DAO.tests;


import java.sql.Date;

import DAO.CompaniesDAO;
import DAO.CompaniesDBDAO;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import beans.Category;
import beans.Company;
import beans.Coupon;
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
//			CompaniesDAO companiesDAO = new CompaniesDBDAO();
//			String name = "company4";
//			String email = "email4";
//			String password = "passwordNew";
//			Company company = new Company(name, email, password);
//			companiesDAO.addCompany(company);
//			System.out.println(companiesDAO.isCompnyExists(email, password)); 
//			companiesDAO.updateCompany(company);
//			companiesDAO.deleteCompany(1);
//			System.out.println(companiesDAO.getAllCompanies());
//			System.out.println(companiesDAO.getOneCompany(2));
			
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
//			couponsDAO.addCoupon(coupon);
//			coupon.setId(4);
//			couponsDAO.updateCoupon(coupon);
//			couponsDAO.deleteCoupon(coupon.getId());
//			System.out.println(couponsDAO.getAllCoupons());
//			System.out.println(couponsDAO.getOneCoupon(5));
//			couponsDAO.addCouponPurchase(1, 5);
			couponsDAO.deleteCouponPurchase(1, 5);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
