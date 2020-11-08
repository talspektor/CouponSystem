package DAO;

import java.util.ArrayList;

import beans.Coupon;
import excetion.CouponSystemException;

public interface CouponsDAO {
	
	public void addCoupon(Coupon coupon) throws CouponSystemException;
	public void updateCoupon(Coupon coupon) throws CouponSystemException;
	public void deleteCoupon(int couponId) throws CouponSystemException;
	public ArrayList<Coupon> getAllCoupons() throws CouponSystemException;
	public Coupon getOneCoupon(int couponId) throws CouponSystemException;
	public void addCouponPurchase(int customerId, int couponId) throws CouponSystemException;
	public void deleteCouponPurchase(int customeId, int couponId) throws CouponSystemException;
}
