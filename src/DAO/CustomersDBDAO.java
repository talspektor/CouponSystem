package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Customer;
import connection.ConnectionPool;
import excetion.CouponSystemException;

public class CustomersDBDAO implements CustomesDAO {
	
	private ConnectionPool connectionPool;

	public CustomersDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	/**
	 * @param: String email, String password
	 * @Return true is customer with this email and password is in database
	 */
	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select id from coupon_system.customers"
				+ " where email=? and"
				+ " password=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setString(1, email);
			pStatement.setString(2, password);
			ResultSet rs = pStatement.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to check if customer exists", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param email
	 * @return
	 * @throws CouponSystemException
	 */
	//TODO: test it
	public boolean isEmailExisted(String email) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select email from coupon_system.customers"
				+ " where email=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setString(1, email);
			ResultSet resultSet = pStatement.executeQuery(sql);
			return resultSet.next();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to connect", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: Customer customer
	 * Add customer to database
	 */
	@Override
	public void addCustomer(Customer customer) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "insert into coupon_system.customers"
				+ " (first_name, last_name, email, password)"
				+ " values(?,?,?,?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to add customer.", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: Customer customer
	 * Update the customer in database
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "update coupon_system.customers"
				+ " set first_name=?,"
				+ " last_name=?,"
				+ " email=?,"
				+ " password=?"
				+ " where id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			System.out.println(pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to update customer.", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: int customerId
	 * Delete customer where id = customerId from database 
	 */
	@Override
	public void deleteCustomer(int customerId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete from coupon_system.customers"
				+ " where id=?";
		try (PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql)){		
			pstmt.setInt(1, customerId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to delete customer.", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @return all customers from database
	 */
	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.customers";
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			ArrayList<Customer> customers = new ArrayList<Customer>();
			while (rs.next()) {
				int id = rs.getInt("id");
				customers.add(getCustomer(id, rs));
			}
			return customers;
		} catch (SQLException e) {
			throw new CouponSystemException("fail to get all customers.", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: int customerId
	 * @return customer where id = customerId
	 */
	@Override
	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.customers where id='" + customerId + "'";
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return getCustomer(customerId, rs);
			}
			throw new CouponSystemException("Customer not found.");
		} catch (SQLException e) {
			throw new CouponSystemException("fail to get customer", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
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
			throw new CouponSystemException("fail to get customer.", e);
		}
	}

}
