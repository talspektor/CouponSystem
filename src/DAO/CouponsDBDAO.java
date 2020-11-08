package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Category;
import beans.Coupon;
import connection.ConnectionPool;
import excetion.CouponSystemException;
import utils.Constants;

public class CouponsDBDAO implements CouponsDAO {
	
	private ConnectionPool connectionPool;
	
	public CouponsDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	/**
	 *param: Coupon coupon
	 *Add coupon to database
	 */
	@Override
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "insert into " + Constants.COUPONS_TABLE + " values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			
			pstmt.setInt(1, coupon.getId());
			pstmt.setInt(2, coupon.getCompanyId());
			pstmt.setObject(3, coupon.getCategory());
			pstmt.setString(4, coupon.getTitle());
			pstmt.setString(5, coupon.getDescription());
			pstmt.setDate(6, coupon.getStartDate());
			pstmt.setDate(7, coupon.getEndDate());
			pstmt.setInt(8, coupon.getAmount());
			pstmt.setDouble(9, coupon.getPrice());
			pstmt.setString(10, coupon.getImageUrl());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * param: Coupon coupon
	 * Update coupon in database
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "update " + Constants.COUPONS_TABLE +
					" set compamyId=?,"
					+ " category=?,"
					+ " title=?,"
					+ " description=?,"
					+ " startDate=?,"
					+ " endDate=?,"
					+ " amount=?,"
					+ " price=?,"
					+ " image=?,"
					+ "  where id=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			pstmt.setInt(1, coupon.getId());
			pstmt.setInt(2, coupon.getCompanyId());
			pstmt.setObject(3, coupon.getCategory());
			pstmt.setString(4, coupon.getTitle());
			pstmt.setString(5, coupon.getDescription());
			pstmt.setDate(6, coupon.getStartDate());
			pstmt.setDate(7, coupon.getEndDate());
			pstmt.setInt(8, coupon.getAmount());
			pstmt.setDouble(9, coupon.getPrice());
			pstmt.setString(10, coupon.getImageUrl());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * param: int couponId
	 * delete coupon from database
	 */
	@Override
	public void deleteCoupon(int couponId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "delete from " + Constants.COUPONS_TABLE + " where id=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * Return all coupons from database
	 */
	@Override
	public ArrayList<Coupon> getAllCoupons() throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "select * from " + Constants.COUPONS_TABLE;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (rs.next()) {
				
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory((Category)rs.getObject("category"));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImageUrl(rs.getString("image"));
				
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * param: int couponId
	 * Return coupon from database
	 */
	@Override
	public Coupon getOneCoupon(int couponId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "select * from " + Constants.COUPONS_TABLE + " where id=" + couponId;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				
				Coupon coupon = new Coupon();
				coupon.setId(couponId);
				coupon.setCompanyId(rs.getInt("company_id"));
				coupon.setCategory((Category)rs.getObject("category"));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImageUrl(rs.getString("image"));
				return coupon;
			}
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
		return null;
	}

	/**
	 * param: int customerId, int couponId
	 * Connect coupon to customer in database
	 */
	@Override
	public void addCouponPurchase(int customerId, int couponId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "insert into " + Constants.CUSTOMERS_VS_COUPONS + " values(?,?)";
			PreparedStatement pstmt = connectionPool.getConnection().prepareStatement(sql);

			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
		
	}

	/**
	 * param: int customerId, int couponId
	 * Remove coupon connection from customer in database  
	 */
	@Override
	public void deleteCouponPurchase(int customeId, int couponId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		try {
			String sql = "delete from " + Constants.CUSTOMERS_VS_COUPONS + " where customer_id=? and coupon_id=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, customeId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

}
