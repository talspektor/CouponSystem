package DAO;

import java.util.ArrayList;

import beans.Coupon;
import excetion.CouponSystemException;

public interface CouponsDAO {
	
	public void deleteExpierdCoupons() throws CouponSystemException;
	public boolean isCouponExists(String title, int companyId) throws CouponSystemException;
	public void addCoupon(Coupon coupon) throws CouponSystemException;
	public void updateCoupon(Coupon coupon) throws CouponSystemException;
	public void deleteCoupon(int couponId) throws CouponSystemException;
	public ArrayList<Coupon> getAllCoupons() throws CouponSystemException;
	public Coupon getOneCoupon(int couponId) throws CouponSystemException;
	public void addCouponPurchase(int customerId, int couponId) throws CouponSystemException;
	public void deleteCouponPurchase(int customeId, int couponId) throws CouponSystemException;
	public void deleteCouponsByCoumpanyId(int companyId) throws CouponSystemException;
	public void deleteCouponPurchaceByCompanyId(int companyId) throws CouponSystemException;
	public void deleteCouponPurchaceByCustomerId(int customerId) throws CouponSystemException;
	public void deleteCoutonPurchaceByCouponId(int couponId) throws CouponSystemException;
	public ArrayList<Coupon> getAllCompanyCoupons(int companyId) throws CouponSystemException;
	public ArrayList<Coupon> getAllCompanyCouponsByCategory(int categoryId, int companyId) throws CouponSystemException;
	public ArrayList<Coupon> getAllCompanyCouponsMaxPrice(double maxPrice, int companyId) throws CouponSystemException;
	public ArrayList<Coupon> getAllCustomerCoupons(int customerId) throws CouponSystemException;
	public ArrayList<Coupon> getAllCustomerCouponsForCategoty(int categoryId, int customerId) throws CouponSystemException;
}
