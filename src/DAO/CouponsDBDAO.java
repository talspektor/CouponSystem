package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import DAO.constants.CouponColumns;
import DAO.constants.CustomersVsCouponsColumns;
import DAO.constants.DbConstants;
import DAO.constants.Tables;
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
		String sql = "select id from "+DbConstants.DB_NAME+"." +Tables.COUPONS
				+ " where "+CouponColumns.TITLE+"=? and"
				+ " "+CouponColumns.COMPANY_ID+"=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setString(1, title);
			pStatement.setInt(2, companyId);
			ResultSet resultSet = pStatement.executeQuery();
			return resultSet.next();
		} catch (Exception e) {
			throw new CouponSystemException("isCouponExists fail: " + e.getMessage(), e);
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
		String sql = "insert into "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " ("+CouponColumns.COMPANY_ID+","
				+ " "+CouponColumns.CATEGORY_ID+","
				+ " "+CouponColumns.TITLE+","
				+ " "+CouponColumns.DESCRIPTION+","
				+ " "+CouponColumns.START_DATE+","
				+ " "+CouponColumns.END_DATE+","
				+ " "+CouponColumns.AMOUNT+","
				+ " "+CouponColumns.PRICE+","
				+ " "+CouponColumns.IMAGE+")"
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
		} catch (Exception e) {
			throw new CouponSystemException("addCoupon fail: " + e.getMessage(), e);
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
		String sql = "update "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " set "+CouponColumns.CATEGORY_ID+"=?,"
				+ " "+CouponColumns.TITLE+"=?,"
				+ " "+CouponColumns.DESCRIPTION+"=?,"
				+ " "+CouponColumns.START_DATE+"=?,"
				+ " "+CouponColumns.END_DATE+"=?,"
				+ " "+CouponColumns.AMOUNT+"=?,"
				+ " "+CouponColumns.PRICE+"=?,"
				+ " "+CouponColumns.IMAGE+"=?"
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
		} catch (Exception e) {
			throw new CouponSystemException("updateCoupon fail: " + e.getMessage(), e);
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
		String sql = "delete from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " where id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, couponId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CouponSystemException("deleteCoupon fail: " + e.getMessage(), e);
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
		String sql = "delete "+DbConstants.DB_NAME+"."+Tables.COUPONS+", "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " inner join "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " on "+Tables.COUPONS+".id="+Tables.CUSTOMER_VS_COUPONS+"."+CustomersVsCouponsColumns.COUPON_ID
				+ " where "+Tables.COUPONS+"."+CouponColumns.END_DATE+"<?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setDate(1, new Date(Calendar.getInstance().getTime().getTime()));
			pStatement.executeUpdate();
		} catch (Exception e) {
			throw new CouponSystemException("deleteExpierdCoupons fail: " + e.getMessage(), e);
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
		String sql = "select * from "+DbConstants.DB_NAME+"."+Tables.COUPONS;
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (rs.next()) {
				coupons.add(getCoupon(rs));
			}
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getAllCoupons fail: " + e.getMessage(), e);
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
		String sql = "select * from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " where "+CouponColumns.COMPANY_ID+"=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, companyId);
			ResultSet resultSet = pStatement.executeQuery();
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getAllCompanyCoupons fail: " + e.getMessage(), e);
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
		String sql = "select * from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " where "+CouponColumns.COMPANY_ID+"=? and"
				+ " "+CouponColumns.PRICE+"<?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, companyId);
			pStatement.setDouble(2, maxPrice);
			ResultSet resultSet = pStatement.executeQuery();
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getAllCompanyCouponsMaxPrice fail: " + e.getMessage(), e);
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
		String sql = "select * from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " where "+CouponColumns.CATEGORY_ID+"=? and"
				+ " "+CouponColumns.COMPANY_ID+"=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, categoryId);
			pStatement.setInt(2, companyId);
			ResultSet resultSet = pStatement.executeQuery();
					ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getAllCompanyCouponsByCategory fail: " + e.getMessage(), e);
		} finally {
			connectionPool.restoreConnection(connection);
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
		String sql = "select * from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " where (select "+CustomersVsCouponsColumns.COUPON_ID
				+ " from "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " where "+CustomersVsCouponsColumns.CUSTOMER_ID+"=?)";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, customerId);
			ResultSet resultSet = pStatement.executeQuery();
					ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getAllCustomerCoupons fail: " + e.getMessage(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	/**
	 * @param categoryId
	 * @return all coupons for customer with given id and category
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getAllCustomerCouponsForCategoty(int categoryId, int customerId) throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "select * from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " where (select "+CustomersVsCouponsColumns.COUPON_ID
				+ " from "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " where "+CouponColumns.CATEGORY_ID+"=? and"
				+ " "+CustomersVsCouponsColumns.CUSTOMER_ID+"=?)";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, categoryId);
			pStatement.setInt(2, customerId);
			ResultSet resultSet = pStatement.executeQuery();
					ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getAllCustomerCouponsForCategoty fail: " + e.getMessage(), e);
		} finally {
			connectionPool.restoreConnection(connection);
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
		String sql = "select * from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " where (select "+CouponColumns.COMPANY_ID
				+ " from "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " where "+CustomersVsCouponsColumns.CUSTOMER_ID+"=?) and "+CouponColumns.PRICE+"<?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){
			pStatement.setInt(1, customerId);
			pStatement.setDouble(2, maxPrice);
			ResultSet resultSet = pStatement.executeQuery();
					ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupons.add(getCoupon(resultSet));
			}
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getAllCustomerCouponsUpToMaxPrice fail: " + e.getMessage(), e);
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
		String sql = "select * from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " where id=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, couponId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return getCoupon(rs);
			}
			System.out.println("coupon with id=" + couponId + " not found in database");
			return null;
		} catch (Exception e) {
			throw new CouponSystemException("getOneCoupon fail: " + e.getMessage(), e);
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
		String sql = "delete from "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " where company_id=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, companyId);
			pstmt.executeUpdate();
		} catch (Exception e) {
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
		String sql = "insert into "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " ("+CustomersVsCouponsColumns.CUSTOMER_ID+","
				+ " "+CustomersVsCouponsColumns.COUPON_ID+")"
				+ " values(?,?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql);){
			pstmt.setInt(1, customerId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CouponSystemException("addCouponPurchase fail: " + e.getMessage(), e);
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
		String sql = "delete from "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " where "+CustomersVsCouponsColumns.CUSTOMER_ID+"=? and "+CustomersVsCouponsColumns.COUPON_ID+"=?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)){
			pstmt.setInt(1, customeId);
			pstmt.setInt(2, couponId);
			pstmt.executeUpdate();
		} catch (Exception e) {
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
		String sql = "delete "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " from "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " inner join "+DbConstants.DB_NAME+"."+Tables.COUPONS
				+ " on "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS+"."+CustomersVsCouponsColumns.COUPON_ID+"="+DbConstants.DB_NAME+"."+Tables.COUPONS+".id"
				+ " where "+DbConstants.DB_NAME+"."+Tables.COUPONS+"."+CouponColumns.COMPANY_ID+"=?";
		try(PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setInt(1, companyId);
			pStatement.executeUpdate();
			pStatement.toString();
		} catch (Exception e) {
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
		String sql = "delete from "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " where "+CustomersVsCouponsColumns.CUSTOMER_ID+"=?";
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
		String sql = "delete from "+DbConstants.DB_NAME+"."+Tables.CUSTOMER_VS_COUPONS
				+ " where "+CustomersVsCouponsColumns.COUPON_ID+"=?";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
			pStatement.setInt(1, couponId);
			pStatement.executeUpdate();
			setSafeUpdateOn();
		} catch (Exception e) {
			throw new CouponSystemException("deleteCoutonPurchaceByCouponId fail: " + e.getMessage(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	private Coupon getCoupon(ResultSet rs) throws CouponSystemException {
		try {
			int id = rs.getInt("id");
			int companyId = rs.getInt(CouponColumns.COMPANY_ID);
			int categoryId = rs.getInt(CouponColumns.CATEGORY_ID);
			String title = rs.getString(CouponColumns.TITLE);
			String description = rs.getString(CouponColumns.DESCRIPTION);
			Date startDate = rs.getDate(CouponColumns.START_DATE);
			Date endDate = rs.getDate(CouponColumns.END_DATE);
			int amount = rs.getInt(CouponColumns.AMOUNT);
			double price = rs.getDouble(CouponColumns.AMOUNT);
			String imageUrl = rs.getString(CouponColumns.IMAGE);
			return new Coupon(id, companyId, categoryId, title, description, startDate, endDate, amount, price, imageUrl);
			
		} catch (Exception e) {
			throw new CouponSystemException("getCoupon fail: " + e.getMessage(), e);
		} 
	}
	
	private void setSafeUpdateOff() throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "SET SQL_SAFE_UPDATES = 0";
		try (Statement statement = connection.createStatement()) {
			statement.executeQuery(sql);
		} catch (Exception e) {
			throw new CouponSystemException("setSafeUpdateOn fail: " + e.getMessage(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}
	
	private void setSafeUpdateOn() throws CouponSystemException {
		Connection connection = connectionPool.getConnection();
		String sql = "SET SQL_SAFE_UPDATES = 1";
		try (Statement statement = connection.createStatement()) {
			statement.executeQuery(sql);
		} catch (Exception e) {
			throw new CouponSystemException("setSafeUpdateOn fai: " + e.getMessage(), e);
		} finally {
			connectionPool.restoreConnection(connection);
		}
	}

}
