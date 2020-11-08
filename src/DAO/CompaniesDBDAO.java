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
			String sql = "select " + Constants.ID + " from " + Constants.COMPANIES_TABLE + " where " + Constants.EMAIL + "=" + email + "AND" + Constants.PAAWORD + "=" + password;
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
		String sql = "insert into " + Constants.COMPANIES_TABLE + "values(?,?,?,?)";
		try {
			setCompany(company, sql);
		} catch (CouponSystemException e) {
			throw e;
		}
	}

	/**
	 * param: company
	 * update the company in the database
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		String sql = "update " + Constants.COMPANIES_TABLE + " set " +
				Constants.NAME + "=?, " +
				Constants.EMAIL + "=?, " +
				Constants.PAAWORD + "=? where " +
				Constants.ID + "=?";
		try {
			setCompany(company, sql);
		} catch (CouponSystemException e) {
			throw e;
		}
	}

	@Override
	public void deleteCompany(int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "delete from " + Constants.COMPANIES_TABLE + " where " + Constants.ID + "=?";
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
			String sql = "select * from " + Constants.COMPANIES_TABLE;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Company> companies = new ArrayList<Company>();
			while (rs.next()) {
				int id = rs.getInt("id");
				companies.add(getCompany(id, rs));
			}
			return companies;
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
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
			String sql = "select * from " + Constants.COMPANIES_TABLE + " where id=" + companyId;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return getCompany(companyId, rs);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
		return null;
	}
	
	private void setCompany(Company company, String sql) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setInt(4, company.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
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
