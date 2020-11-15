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
			throw new CouponSystemException("isCustomerExists fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param email
	 * @return true if email exists
	 * @throws CouponSystemException
	 */
	//TODO: test it
	@Override
	public boolean isEmailExists(String email) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select email from coupon_system.customers"
				+ " where email=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setString(1, email);
			ResultSet resultSet = pStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			throw new CouponSystemException("isEmailExists fail", e);
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
			throw new CouponSystemException("addCustomer fail", e);
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
			throw new CouponSystemException("updateCustomer fail", e);
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
			throw new CouponSystemException("deleteCustomer fail", e);
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
				customers.add(getCustomer(rs));
			}
			return customers;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCustomers fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: int customerId
	 * @return customer where id = customerId
	 * if not found return null
	 */
	@Override
	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.customers"
				+ " where id=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setInt(1, customerId);
			ResultSet rs = pStatement.executeQuery();
			if (rs.next()) {
				return getCustomer(rs);
			}
			System.out.println("Customer not found.");
			return null;
		} catch (SQLException e) {
			throw new CouponSystemException("getOneCustomer fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 *@param email
	 *@param password
	 *@return Customer from database
	 *if not found return null
	 */
	@Override
	public Customer getCustomerByEmailAndPassword(String email, String password) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.customers"
				+ " where email=? and"
				+ "password=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setString(1, email);
			pStatement.setString(2, password);
			ResultSet rs = pStatement.executeQuery();
			if (rs.next()) {
				return getCustomer(rs);
			}
			System.out.println("Customer not found.");
			return null;
		} catch (SQLException e) {
			throw new CouponSystemException("getOneCustomer fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 *@param customerId
	 *@param couponId
	 *@return true if coupon is already been purchased
	 */
	@Override
	public boolean isCouponAlreadyPurchased(int customerId, int couponId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.customers_vs_coupons"
				+ " where customer_id=? and"
				+ "coupon_id=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setInt(1, customerId);
			pStatement.setInt(2, couponId);
			ResultSet rs = pStatement.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CouponSystemException("isCouponAlreadyPurchased fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	private Customer getCustomer(ResultSet rs) throws CouponSystemException {
		try {
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String email = rs.getString("email");
			String password = rs.getString("password");
			int id = rs.getInt("id");
			return new Customer(id, firstName, lastName, email, password);
		} catch (SQLException e) {
			throw new CouponSystemException("getCustomer fail", e);
		}
	}

}
