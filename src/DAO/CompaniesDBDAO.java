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
	 * @param email
	 * @param password
	 * @return true if company exists in database
	 * @throws CouponSystemException
	 */
	@Override
	public boolean isCompnyExists(String email, String password) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select id from coupon_system.companies"
				+ " where email=? and"
				+ "password=?";
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
	 * @param: email
	 * @return true if email is unique
	 */
	@Override
	public boolean isCompnyEmailUnique(String email) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select id from coupon_system.companies"
				+ " where email=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setString(1, email);
			ResultSet rs = pStatement.executeQuery();
			return !rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("isCompnyEmailUnique fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 *@param
	 *@return true is password is unique
	 */
	@Override
	public boolean isCompanyPasswordUnique(String password) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select id from coupon_system.companies"
				+ " where password=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setString(1, password);
			ResultSet rs = pStatement.executeQuery();
			return !rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("isCompanyPasswordUnique fail", e);
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
				+ " set name=?, email=?, password=?"
				+ " where id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setString(1, company.getName());
			pstmt.setString(1, company.getEmail());
			pstmt.setString(2, company.getPassword());
			pstmt.setInt(3, company.getId());
			System.out.println(pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("updateCompany fail", e);
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
			throw new CouponSystemException("deleteCompany fail", e);
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
				companies.add(getCompany(rs));
			}
			return companies;
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("getAllCompanies fail" + e.getCause(), e);
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
				return getCompany(rs);
			}
			System.out.println("company with id=" + companyId + " not faund in database.");
			return null;
		} catch (SQLException e) {
			throw new CouponSystemException("getOneCompany fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param email
	 * @param password
	 * @return Company with given email and password
	 * @throws CouponSystemException
	 */
	public Company getCompany(String email, String password) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.companies"
				+ " where email=? and"
				+ " password=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setString(1, email);
			pStatement.setString(2, password);
			ResultSet resultSet = pStatement.executeQuery();
			if(resultSet.next()) {
				return getCompany(resultSet);
			}
			System.out.println("Company with email=" + email + " and password=" + password + "is not in database");
			return null;
		} catch (SQLException e) {
			throw new CouponSystemException("getCompany fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param resultSet
	 * @return company 
	 * @throws CouponSystemException
	 */
	private Company getCompany(ResultSet resultSet) throws CouponSystemException {
		try {
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			String password = resultSet.getString("password");
			int id = resultSet.getInt("id");
			return new Company(id, name, email, password);
		} catch (SQLException e) {
			throw new CouponSystemException("fail to get company.", e);
		}
		
	}

}
