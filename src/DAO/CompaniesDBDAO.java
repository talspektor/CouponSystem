package DAO;

import java.sql.Connection;
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
	
	public CompaniesDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	/**
	 * param: email. password
	 * return true if company is in database / return false if not
	 */
	@Override
	public boolean isCompnyExists(String email, String password) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "select id from coupon_system.companies where email='" + email + "' AND password='" + password + "'";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * param: company
	 * add the company to the database 
	 */
	@Override
	public void addCompany(Company company) throws CouponSystemException {
		String sql = "insert into coupon_system.companies (name, email, password) values(?,?,?)";
		Connection connection = connectionPool.getConnection();
		try {
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * param: company
	 * update the company in the database
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "update coupon_system.companies set name=?, email=?, password=? where id=?";
		try {
			PreparedStatement pstmt;
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setInt(4, company.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}  finally {
			connectionPool.restoreConnection(connection);
		}
	}

	@Override
	public void deleteCompany(int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "delete from coupon_system.companies where id=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * return all the companies from the database
	 */
	@Override
	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "select * from coupon_system.companies";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Company> companies = new ArrayList<Company>();
			while (rs.next()) {
				int id = rs.getInt("id");
				companies.add(getCompany(id, rs));
			}
			return companies;
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("fail to get all compnies: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * param: companyId
	 * return the company with the id=companyId from database
	 */
	@Override
	public Company getOneCompany(int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "select * from coupon_system.companies where id=" + companyId;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return getCompany(companyId, rs);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("fail to get compny: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
		return null;
	}
	
	private Company getCompany(int companyId, ResultSet rs) throws CouponSystemException {
		Company company = new Company();
		try {
			company.setName(rs.getString("name"));
			company.setEmail(rs.getString("email"));
			company.setPassword(rs.getString("password"));
			company.setId(rs.getInt("id"));
			return company;
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
		
	}

}
