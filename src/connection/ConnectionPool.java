package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author taltalspektor
 * manage the system connections
 */
public class ConnectionPool {

	private Set<Connection> connections = new HashSet<Connection>();
	public static final int MAX = 5;// the maximum connections in the pool
	private String url;

	private static ConnectionPool instance;

	private ConnectionPool() throws SQLException {
		for (int i = 0; i < MAX; i++) {
			connections.add(DriverManager.getConnection(url));
		}
	}
	
	public static ConnectionPool getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	/**
	 * @return a connection
	 * if no connection is available will notify the current thread to wait
	 */
	public synchronized Connection getConnection() {
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
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
		connections.add(connection);
		notify();
	}
	/**
	 * Close all the connections
	 */
	public void closeAllConnections() {
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
