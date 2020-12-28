package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import excetion.CouponSystemException;

/**
 * @author tal spektor
 * manage the system connections
 */
public class ConnectionPool {

	private Set<Connection> connections = new HashSet<Connection>();
	public static final int MAX = 5;// the maximum connections in the pool
	/**
	 * url to connect mysql coupons_system database
	 */
	private String url = "jdbc:mysql://localhost:3306/coupon_system";

	private static ConnectionPool instance;
	private int closeConnectionCount;
	private int openConnectionCount;

	/**
	 * @throws CouponSystemException
	 */
	private ConnectionPool() throws CouponSystemException {
		for (int i = 0; i < MAX; i++) {
			try {
				connections.add(DriverManager.getConnection(url, "test", "testpassword"));
			} catch (SQLException e) {
				throw new CouponSystemException("ConnectionPool constaructor fail: " + e.getMessage() , e);
			}
		}
	}
	
	/**
	 * @return ConnectionPool
	 * @throws CouponSystemException
	 */
	public static ConnectionPool getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	/**
	 * @return a connection
	 * if no connection is available will notify the current thread to wait
	 */
	public synchronized Connection getConnection() throws CouponSystemException {
		openConnectionCount++;
		System.out.println(">>>>>>>>>>>>>>>>getConnection " + openConnectionCount);
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				new CouponSystemException("getConnection fail: " + e.getMessage(), e);
			}
		}
		Iterator<Connection> it = connections.iterator();
		Connection con = it.next();
		it.remove();
		return con;
	}
	/**
	 * @param connection
	 * Restore a connection back in the connection set
	 */
	public synchronized void restoreConnection(Connection connection) {
		closeConnectionCount++;
		System.out.println("<<<<<<<<<<<<<<<<<restoreConnection " + closeConnectionCount);
		connections.add(connection);
		notify();
	}
	/**
	 * Close all the connections
	 */
	public synchronized void closeAllConnections() throws CouponSystemException {
		System.out.println(connections.size());
		//TODO: close the pool from getting connections out 
		while (connections.size() != MAX) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (SQLException e) {
				new CouponSystemException("closeAllConnections fail: " + e.getMessage(), e);
			}
		}
		System.out.println("closeAllConnections");
	}
}
