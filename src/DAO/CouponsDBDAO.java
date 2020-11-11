package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Coupon;
import connection.ConnectionPool;
import excetion.CouponSystemException;

public class CouponsDBDAO implements CouponsDAO {
	
	private ConnectionPool connectionPool;
	
	public CouponsDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	/**
	 *@param: Coupon coupon
	 *Add coupon to database
	 */
	@Override
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "insert into coupon_system.coupons"
				+ " (company_id,"
				+ " category_id,"
				+ " title,"
				+ " description,"
				+ " start_date,"
				+ " end_date,"
				+ " amount,"
				+ " price,"
				+ " image)"
				+ " values(?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setInt(2, coupon.getCategoryId());
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, coupon.getStartDate());
			pstmt.setDate(6, coupon.getEndDate());
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImageUrl());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to add coupon.", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: Coupon coupon
	 * Update coupon in database
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "update coupon_system.coupons"
				+ " set company_id=?,"
				+ " category_id=?,"
				+ " title=?,"
				+ " description=?,"
				+ " start_date=?,"
				+ " end_date=?,"
				+ " amount=?,"
				+ " price=?,"
				+ " image=?"
				+ " where id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, coupon.getCompanyId());
			pstmt.setInt(2, coupon.getCategoryId());
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, coupon.getStartDate());
			pstmt.setDate(6, coupon.getEndDate());
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImageUrl());
			pstmt.setInt(10, coupon.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to update coupon.", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: int couponId
	 * delete coupon from database
	 */
	@Override
	public void deleteCoupon(int couponId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete from coupon_system.coupons"
				+ " where id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to delete coupon.", e);
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
		String sql = "select * from coupon_system.coupons";
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (rs.next()) {
				int id = rs.getInt("id");
				coupons.add(getCoupon(id, rs));
			}
			return coupons;
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("fail to return coupons.", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: int couponId
	 * @return coupon from database
	 */
	@Override
	public Coupon getOneCoupon(int couponId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.coupons"
				+ " where id=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, couponId);
			ResultSet rs = preparedStatement.executeQuery(sql);
			if (rs.next()) {
				return getCoupon(couponId, rs);
			}
			throw new CouponSystemException("Coupon not found.");
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("fail: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param companyId
	 * @throws CouponSystemException
	 * delete all coupons with the companyId
	 */
	//TODO: test it
	public void deleteCouponsByCoumpanyId(int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete from coupon_system.coupons"
				+ " where company_id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, companyId);
			System.out.println(pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to delete coupons", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * @param: int customerId, int couponId Connect coupon to customer in database
	 */
	@Override
	public void addCouponPurchase(int customerId, int couponId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "insert into coupon_system.customers_vs_coupons"
				+ " values(?,?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql);){
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to add coupon purchase: " + e.getCause(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
		
	}

	/**
	 * @param: int customerId, int couponId
	 * Remove coupon connection from customer in database  
	 */
	@Override
	public void deleteCouponPurchase(int customeId, int couponId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete from coupon_system.customers_vs_coupons"
				+ " where customer_id=? and coupon_id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, customeId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to delete coupon purchase", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param couponId
	 * @throws CouponSystemException
	 * delete all coupon purchaces with this coupon id from database 
	 */
	//TODO: test it
	public void deleteCouponPurchaceByCompanyId(int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete coupon_system.coupons, coupon_system.customers_vs_coupons"
				+ " from coupon_system.coupons"
				+ " inner join coupon_system.customers_vs_coupons"
				+ "on coupons.id=customers_vs_coupons.coupon_id"
				+ "where coupons.company_id=?";
		try(PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setInt(1, companyId);
			pStatement.toString();
		} catch (SQLException e) {
			throw new CouponSystemException("fail to delete coupons", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param customerId
	 * @throws CouponSystemException
	 * delete coupon purrchaces with this customer id from database 
	 */
	//TODO: test it
	public void deleteCouponPurchaceByCustomerId(int customerId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete from coupon_system.customers_vs_coupons"
				+ " where customer_id=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, customerId);
		} catch (Exception e) {
			throw new CouponSystemException("fail to delete coupons", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	private Coupon getCoupon(int couponId, ResultSet rs) throws CouponSystemException {
		try {
			int id = couponId;
			int companyId = rs.getInt("company_id");
			int categoryId = rs.getInt("category_id");
			String title = rs.getString("title");
			String description = rs.getString("description");
			Date startDate = rs.getDate("start_date");
			Date endDate = rs.getDate("end_date");
			int amount = rs.getInt("amount");
			double price = rs.getDouble("price");
			String imageUrl = rs.getString("image");
			return new Coupon(id, companyId, categoryId, title, description, startDate, endDate, amount, price, imageUrl);
			
		} catch (SQLException e) {
			throw new CouponSystemException("fail to get coupon", e);
		} 
	}

}
