package job;

import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import excetion.CouponSystemException;

public class DailyJob implements Runnable {
	private CouponsDAO couponsDAO;
	private boolean quit;
	private final int daysInMilliseconds = 1000*60*60*24;
		
	public DailyJob(CouponsDBDAO couponsDAO) throws CouponSystemException {
		super();
		this.couponsDAO = couponsDAO;
		this.quit = false;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("CouponExpirationDailyJob run");
			while (!quit) {
				couponsDAO.deleteExpierdCoupons();
				System.out.println("CouponExpirationDailyJob deleteExpierdCoupons");
				Thread.sleep(daysInMilliseconds);
			}
		} catch (CouponSystemException | InterruptedException e) {
			System.out.println(e.getCause());
		}
	}
	
	public void stop() {
		this.quit = true;
	}
}
