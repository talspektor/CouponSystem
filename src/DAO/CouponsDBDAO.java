package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import beans.Coupon;
import connection.ConnectionPool;
import excetion.CouponSystemException;

public class CouponsDBDAO implements CouponsDAO {
	
	private ConnectionPool connectionPool;
	
	public CouponsDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}
	
	/**
	 * @param title
	 * @param companyId
	 * @return true if coupon with this title and company id is in database
	 * @throws CouponSystemException
	 */
	public boolean isCouponExists(String title, int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select id from coupon_system.coupons"
				+ " where title=? and"
				+ " company_id=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setString(1, title);
			pStatement.setInt(2, companyId);
			System.out.println(pStatement.toString());
			ResultSet resultSet = pStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			throw new CouponSystemException("isCouponExists fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
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
			throw new CouponSystemException("addCoupon fail", e);
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
				+ " set category_id=?,"
				+ " title=?,"
				+ " description=?,"
				+ " start_date=?,"
				+ " end_date=?,"
				+ " amount=?,"
				+ " price=?,"
				+ " image=?"
				+ " where id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, coupon.getCategoryId());
			pstmt.setString(2, coupon.getTitle());
			pstmt.setString(3, coupon.getDescription());
			pstmt.setDate(4, coupon.getStartDate());
			pstmt.setDate(5, coupon.getEndDate());
			pstmt.setInt(6, coupon.getAmount());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImageUrl());
			pstmt.setInt(9, coupon.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("updateCoupon fail", e);
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
			throw new CouponSystemException("deleteCoupon fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @throws CouponSystemException
	 * delete all expired coupons from database
	 */
	//TODO: test
	public void deleteExpierdCoupons() throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete coupon_system.coupons, coupon_system.customers_vs_coupons"
				+ " from coupon_system.coupons"
				+ " inner join coupon_system.customers_vs_coupons"
				+ " on coupons.id=customers_vs_coupons.coupon_id"
				+ " where coupons.end_date<?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setDate(1, new Date(Calendar.getInstance().getTime().getTime()));
			pStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteExpierdCoupons fail", e);
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
				coupons.add(getCoupon(rs));
			}
			return coupons;
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("getAllCoupons fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 *@param companyId
	 *@return all coupons with given company id
	 */
	@Override
	public ArrayList<Coupon> getAllCompanyCoupons(int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.coupons"
				+ " where company_id=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, companyId);
			ResultSet resultSet = pStatement.executeQuery();
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCompanyCoupons fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 *@param maxPrice
	 *@param companyId
	 *@return list of coupons with company id and price less then maxPrice
	 */
	@Override
	public ArrayList<Coupon> getAllCompanyCouponsMaxPrice(double maxPrice, int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.coupons"
				+ " where company_id=? and"
				+ " price<?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, companyId);
			pStatement.setDouble(2, maxPrice);
			ResultSet resultSet = pStatement.executeQuery();
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCompanyCouponsMaxPrice fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param categoryId
	 * @param companyId
	 * @return all coupons with given category id and company id
	 * @throws CouponSystemException
	 */
	@Override
	public ArrayList<Coupon> getAllCompanyCouponsByCategory(int categoryId, int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.coupons"
				+ " where category_id=? and"
				+ " company_id=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, categoryId);
			pStatement.setInt(2, companyId);
			ResultSet resultSet = pStatement.executeQuery();
					ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCompanyCouponsByCategory fail", e);
		}
	}
	
	/**
	 * @param customerId
	 * @return all coupons for customer with the given customerId
	 * @throws CouponSystemException
	 */
	@Override
	public ArrayList<Coupon> getAllCustomerCoupons(int customerId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.coupons"
				+ " where (select coupon_id"
				+ " from coupon_system.customers_vs_coupons"
				+ " where customer_id=?)";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, customerId);
			ResultSet resultSet = pStatement.executeQuery();
					ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCustomerCoupons fail", e);
		}
	}
	
	/**
	 * @param categoryId
	 * @return all coupons for customer with given id and category
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getAllCustomerCouponsForCategoty(int categoryId, int customerId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.coupons"
				+ " where (select coupon_id"
				+ " from coupon_system.customers_vs_coupons"
				+ " where category_id=? and"
				+ " customer_id=?)";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, categoryId);
			pStatement.setInt(2, customerId);
			ResultSet resultSet = pStatement.executeQuery();
					ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCustomerCouponsForCategoty fail", e);
		}
	}
	
	/**
	 * @param maxPrice
	 * @param customerId
	 * @return all coupons for customer up to maxPrice
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getAllCustomerCouponsUpToMaxPrice(double maxPrice, int customerId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from coupon_system.coupons"
				+ " where (select coupon_id"
				+ " from coupon_system.customers_vs_coupons"
				+ " where customerId=?) and price<?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, customerId);
			pStatement.setDouble(2, maxPrice);
			ResultSet resultSet = pStatement.executeQuery();
					ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCustomerCouponsUpToMaxPrice fail", e);
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
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return getCoupon(rs);
			}
			System.out.println("coupon with id=" + couponId + " not found in database");
			return null;
		} catch (SQLException | ClassCastException e) {
			throw new CouponSystemException("getOneCoupon fail" + couponId, e);
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
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteCouponsByCoumpanyId fail: " + e.getMessage(), e);
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
				+ " (custome_id,"
				+ " coupon_id)"
				+ " values(?,?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql);){
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("addCouponPurchase fail:, customr"
					+ " id=" + customerId + " coupon id=" + couponId + e.getMessage(), e);
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
			throw new CouponSystemException("deleteCouponPurchase fail: " + e.getMessage(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param couponId
	 * @throws CouponSystemException
	 * delete all coupon purchases with this coupon id from database 
	 */
	//TODO: test it
	@Override
	public void deleteCouponPurchaceByCompanyId(int companyId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete coupon_system.customers_vs_coupons"
				+ " from coupon_system.customers_vs_coupons"
				+ " inner join coupon_system.coupons"
				+ " on coupon_system.customers_vs_coupons.coupon_id=coupon_system.coupons.id"
				+ " where coupon_system.coupons.company_id=?";
		try(PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setInt(1, companyId);
			pStatement.executeUpdate();
			pStatement.toString();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteCouponPurchaceByCompanyId fail: " + e.getMessage(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param customerId
	 * @throws CouponSystemException
	 * delete coupon purchases with this customer id from database 
	 */
	//TODO: test it
	@Override
	public void deleteCouponPurchaceByCustomerId(int customerId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "delete from coupon_system.customers_vs_coupons"
				+ " where customer_id=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, customerId);
			pStatement.executeUpdate();
		} catch (Exception e) {
			throw new CouponSystemException("deleteCouponPurchaceByCustomerId fail: " + e.getMessage(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param couponId
	 * delete coupon purchase for database
	 */
	public void deleteCoutonPurchaceByCouponId(int couponId) throws CouponSystemException {
		setSafeUpdateOff();
		Connection connection = connectionPool.getConnection();
		String sql = "delete from coupon_system.customers_vs_coupons"
				+ " where coupon_id=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setInt(1, couponId);
			pStatement.executeUpdate();
			setSafeUpdateOn();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteCoutonPurchaceByCouponId fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	private Coupon getCoupon(ResultSet rs) throws CouponSystemException {
		try {
			int id = rs.getInt("id");
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
			throw new CouponSystemException("getCoupon fail", e);
		} 
	}
	
	private void setSafeUpdateOff() throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "SET SQL_SAFE_UPDATES = 0";
		try (Statement statement = connection.createStatement()) {
			statement.executeQuery(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("setSafeUpdateOn fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	private void setSafeUpdateOn() throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "SET SQL_SAFE_UPDATES = 1";
		try (Statement statement = connection.createStatement()) {
			statement.executeQuery(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("setSafeUpdateOn fail", e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

}
