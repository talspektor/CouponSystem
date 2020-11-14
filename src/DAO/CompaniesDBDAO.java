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

public class CompaniesDBDAO implements CompaniesDAO {
	
	private ConnectionPool connectionPool;
	
	public CompaniesDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	/**
	 * @param: email. password
	 * return true if company is in database / return false if not
	 */
	@Override
	public boolean isCompnyExists(String email, String password) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select id from coupon_system.companies"
				+ " where email=? and"
				+ " password=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setString(1, email);
			pStatement.setString(2, password);
			ResultSet rs = pStatement.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("isCompnyExists fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: company
	 * add the company to the database 
	 */
	@Override
	public void addCompany(Company company) throws CouponSystemException {
		String sql = "insert into coupon_system.companies (name, email, password)"
				+ " values(?,?,?)";
		Connection connection = connectionPool.getConnection();
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to add company", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: company
	 * update the company in the database
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "update coupon_system.companies"
				+ " set email=?, password=?"
				+ " where id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setString(1, company.getEmail());
			pstmt.setString(2, company.getPassword());
			pstmt.setInt(3, company.getId());
			System.out.println(pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to update compny.", e);
		}  finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param companyId type int
	 * delete the company with this id from database
	 */
	@Override
	public void deleteCompany(int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete from coupon_system.companies"
				+ " where id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to delete company.", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @return all the companies from the database
	 */
	@Override
	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.companies";
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
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
	 * @param: companyId
	 * return the company with the id=companyId from database
	 */
	@Override
	public Company getOneCompany(int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.companies"
				+ " where id=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, companyId);
			ResultSet rs = pStatement.executeQuery();
			if (rs.next()) {
				return getCompany(companyId, rs);
			}
			throw new CouponSystemException("company not faund in database.");
		} catch (SQLException e) {
			throw new CouponSystemException("fail to get compny with id:" + companyId, e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	private Company getCompany(int companyId, ResultSet rs) throws CouponSystemException {
		try {
			String name = rs.getString("name");
			String email = rs.getString("email");
			String password = rs.getString("password");
			int id = rs.getInt("id");
			return new Company(id, name, email, password);
		} catch (SQLException e) {
			throw new CouponSystemException("fail to get company.", e);
		}
		
	}

}
