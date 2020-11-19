package DAO.tests;

import DAO.CompaniesDAO;
import DAO.CompaniesDBDAO;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import beans.Company;

public class TestCompaniesDBDAO {
	
//	public static void main(String[] args) {
//		// -----TEST CompaniesDBDAO ---------
//		
//		try {
//			CouponsDAO couponsDAO = new CouponsDBDAO();
//			CompaniesDAO companiesDAO = new CompaniesDBDAO();
//			String companyName = "company_";
//			String companyEmail = "email_";
//			String companyPassword = "password_";
//			Company company = new Company();
//			for (int i = 0; i < 10; i++) {
//				companyName = "company_" + i;
//				companyEmail = "email_" + i;
//				companyPassword = "password_" + i;
//				company = new Company(companyName, companyEmail, companyPassword);
////				companiesDAO.addCompany(company);
//			}
//			System.out.println(companiesDAO.isCompnyExists(companyEmail,
//					companyPassword));
//			company.setEmail("new_email");
//			companiesDAO.updateCompany(company);
//			couponsDAO.deleteCouponPurchaceByCompanyId(3);
//			couponsDAO.deleteCouponsByCoumpanyId(2);
//			companiesDAO.deleteCompany(2);
//			System.out.println(companiesDAO.getAllCompanies());
//			System.out.println(companiesDAO.getOneCompany(4));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
