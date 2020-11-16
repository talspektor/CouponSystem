package job;

import DAO.CouponsDAO;
import excetion.CouponSystemException;


public class CouponExpirationDailyJob implements Runnable {

	private CouponsDAO couponsDAO;
	private boolean quit;
	
	public CouponExpirationDailyJob(CouponsDAO couponsDAO) {
		super();
		this.couponsDAO = couponsDAO;
		this.quit = false;
	}

	@Override
	public void run() {
		try {
			couponsDAO.deleteExpierdCoupons();
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop() {
		this.quit = false;
	}

}
