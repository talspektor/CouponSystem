package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Company;
import beans.Customer;
import connection.ConnectionPool;
import excetion.CouponSystemException;
import utils.Constants;

public class CustomersDBDAO implements CustomesDAO {
	
	private ConnectionPool connectionPool;

	public CustomersDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	/**
	 * params: String email, String password
	 * Return true is customer with this email and password is in database
	 */
	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		try {
			String sql = "select id from coupon_system.customers where email='" + email + "' AND password='" + password + "'";
			Statement stmt = connectionPool.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to connect: " + e.getCause(), e);
		}
	}

	/**
	 * param: Customer customer
	 * Add customer to database
	 */
	@Override
	public void addCustomer(Customer customer) throws CouponSystemException {
		try {
			String sql = "insert into coupon_system.customers (first_name, last_name, email, password) values(?,?,?,?)";
			System.out.println(sql);
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);
			
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to add customer: " + e.getCause(), e);
		}
	}

	/**
	 * param: Customer customer
	 * Update the customer in database
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		try {
			String sql = "update coupon_system.customers"
					+ " set first_name=?,"
					+ " last_name=?,"
					+ " email=?,"
					+ " password=?"
					+ " where id=?";
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);

			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to update customer: " + e.getCause(), e);
		}
	}

	/**
	 * param: int customerId
	 * Delete customer where id = customerId from database 
	 */
	@Override
	public void deleteCustomer(int customerId) throws CouponSystemException {
		try {
			String sql = "delete from coupon_system.customers where id=?";
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);
			pstmt.setInt(1, customerId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to delete customer: " + e.getCause(), e);
		}
	}

	/**
	 * Retrun all customers from database
	 */
	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		try {
			String sql = "select * from coupon_system.customers";
			Statement stmt = connectionPool.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Customer> customers = new ArrayList<Customer>();
			while (rs.next()) {
				int id = rs.getInt("id");
				customers.add(getCustomer(id, rs));
			}
			return customers;
		} catch (SQLException e) {
			throw new CouponSystemException("fail to get all customers: " + e.getCause(), e);
		}
	}

	/**
	 * param: int customerId
	 * Return customer where id = customerId
	 */
	@Override
	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		try {
			String sql = "select * from coupon_system.customers where id='" + customerId + "'";
			Statement stmt = connectionPool.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return getCustomer(customerId, rs);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("fail to get customer: " + e.getCause(), e);
		}
		return null;
	}
	
	private Customer getCustomer(int customerId, ResultSet rs) throws CouponSystemException {
		try {
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String email = rs.getString("email");
			String password = rs.getString("password");
			int id = rs.getInt("id");
			return new Customer(id, firstName, lastName, email, password);
		} catch (SQLException e) {
			throw new CouponSystemException("fail" + e.getCause(), e);
		}
	}

}
