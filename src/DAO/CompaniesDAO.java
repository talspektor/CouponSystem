package DAO;

import java.util.ArrayList;

import beans.Company;
import excetion.CouponSystemException;

public interface CompaniesDAO {

	public boolean isCompnyExists(String email, String password) throws CouponSystemException;
	public void addCompany(Company company) throws CouponSystemException;
	public void updateCompany(Company company) throws CouponSystemException;
	public void deleteCompany(int companyId) throws CouponSystemException;
	public ArrayList<Company> getAllCompanies() throws CouponSystemException;
	public Company getOneCompany(int companyId) throws CouponSystemException;
}