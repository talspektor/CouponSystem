package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Company;
import connection.ConnectionPool;
import excetion.CouponSystemException;
import utils.Constants;

public class CompaniesDBDAO implements CompaniesDAO {
	
	private ConnectionPool connectionPool;
	
	public CompaniesDBDAO() throws SQLException {
		connectionPool = ConnectionPool.getInstance();
	}

	/**
	 * param: email. password
	 * return true if company is in database / return false if not
	 */
	@Override
	public boolean isCompnyExists(String email, String password) throws CouponSystemException {
		try {
			String sql = "select id from " + Constants.COMPANIES_TABLE + " where " + Constants.EMAIL + "=" + email + "AND" + Constants.PAAWORD + "=" + password;
			Statement stmt = connectionPool.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
		return false;
	}

	/**
	 * param: company
	 * add the company to the database 
	 */
	@Override
	public void addCompany(Company company) throws CouponSystemException {		
		try {
			String sql = "insert into " + Constants.COMPANIES_TABLE + "values(?,?,?,?)";
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);
			
			pstmt.setInt(1, company.getId());
			pstmt.setString(2, company.getName());
			pstmt.setString(3, company.getEmail());
			pstmt.setString(4, company.getPassword());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
	}

	/**
	 * param: company
	 * update the company in the database
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		try {
			String sql = "update " + Constants.COMPANIES_TABLE + " set name=?, email=?, password=? where id=?";
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);

			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setInt(4, company.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
	}

	@Override
	public void deleteCompany(int companyId) throws CouponSystemException {
		try {
			String sql = "delete from " + Constants.COMPANIES_TABLE + " where id=?";
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
	}

	/**
	 * return all the companies from the database
	 */
	@Override
	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		try {
			String sql = "select * from " + Constants.COMPANIES_TABLE;
			Statement stmt = connectionPool.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Company> companies = new ArrayList<Company>();
			while (rs.next()) {
				
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				companies.add(company);
			}
			return companies;
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
	}

	/**
	 * param: companyId
	 * return the company with the id=companyId from database
	 */
	@Override
	public Company getOneCompany(int companyId) throws CouponSystemException {
		try {
			String sql = "select * from " + Constants.COMPANIES_TABLE + " where id=" + companyId;
			Statement stmt = connectionPool.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				
				Company company = new Company();
				company.setId(companyId);
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				return company;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
		return null;
	}

}
