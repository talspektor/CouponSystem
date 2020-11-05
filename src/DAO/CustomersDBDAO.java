package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Customer;
import connection.ConnectionPool;
import excetion.CouponSystemException;
import utils.Constants;

public class CustomersDBDAO implements CustomesDAO {
	
	private ConnectionPool connectionPool;

	public CustomersDBDAO() throws SQLException {
		connectionPool = ConnectionPool.getInstance();
	}

	/**
	 * params: String email, String password
	 * Return true is customer with this email and password is in database
	 */
	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		try {
			String sql = "select id from " + Constants.CUSTOMERS_TABLE + " where " + Constants.EMAIL + "=" + email + "AND" + Constants.PAAWORD + "=" + password;
			Statement stmt = connectionPool.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
	}

	/**
	 * param: Customer customer
	 * Add customer to database
	 */
	@Override
	public void addCustomer(Customer customer) throws CouponSystemException {
		try {
			String sql = "insert into " + Constants.CUSTOMERS_TABLE + "values(?,?,?,?,?)";
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);
			
			pstmt.setInt(1, customer.getId());
			pstmt.setString(2, customer.getFirstName());
			pstmt.setString(3, customer.getLastName());
			pstmt.setString(4, customer.getEmail());
			pstmt.setString(5, customer.getPassword());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
	}

	/**
	 * param: Customer customer
	 * Update the customer in database
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		try {
			String sql = "update " + Constants.CUSTOMERS_TABLE + " set first_name=?, last_name=?, email=?, password=? where id=?";
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);

			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
	}

	/**
	 * param: int customerId
	 * Delete customer where id = customerId from database 
	 */
	@Override
	public void deleteCustomer(int customerId) throws CouponSystemException {
		try {
			String sql = "delete from " + Constants.CUSTOMERS_TABLE + " where id=?";
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
	}

	/**
	 * Retrun all customers from database
	 */
	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		try {
			String sql = "select * from " + Constants.CUSTOMERS_TABLE;
			Statement stmt = connectionPool.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Customer> customers = new ArrayList<Customer>();
			while (rs.next()) {
				
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				customers.add(customer);
			}
			return customers;
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
	}

	/**
	 * param: int customerId
	 * Return customer where id = customerId
	 */
	@Override
	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		try {
			String sql = "select * from " + Constants.CUSTOMERS_TABLE + " where id=" + customerId;
			Statement stmt = connectionPool.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				
				Customer customer = new Customer();
				customer.setId(customerId);
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				return customer;
			}
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		}
		return null;
	}

}
