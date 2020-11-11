package DAO.tests;

import java.sql.Date;

import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import beans.Coupon;
import excetion.CouponSystemException;

public class TestCouponsDBDAO {

	public static void main(String[] args) {
		
		try {
			// ----- TEST CouponsDBDAO -----
			CouponsDAO couponsDAO = new CouponsDBDAO();
			int companyId = 2;
			int categoryId = 1;
			String title = "newtitle";
			String description = "dxfxadzc";
			Date startDate = new Date(2020, 2, 3);
			Date endDate = new Date(1980, 2, 21);
			int amount = 2;
			double price = 5.5;
			String imageUrl = "asdgadfg";
			Coupon coupon = new Coupon(companyId, categoryId, title, description, startDate, endDate, amount, price, imageUrl);
			for (int i = 5; i < 10; i++) {
				companyId = i;
				categoryId = 3;
				title = "title_"+i;
				description = "description_"+i;
				startDate = new Date(2020, 2, 3);
				endDate = new Date(1980, 2, 21);
				amount = i;
				price = 1.5+i;
				imageUrl = "image_url_"+i;
				coupon = new Coupon(companyId, categoryId, title, description, startDate, endDate, amount, price, imageUrl);
				couponsDAO.addCoupon(coupon);
			}
//			coupon.setTitle("newTitle");
//			couponsDAO.updateCoupon(coupon);
//			couponsDAO.deleteCoupon(11);
//			System.out.println(couponsDAO.getAllCoupons());
//			System.out.println(couponsDAO.getOneCoupon(12));
//			couponsDAO.addCouponPurchase(4, 12);
//			couponsDAO.deleteCouponPurchase(4, 12);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
