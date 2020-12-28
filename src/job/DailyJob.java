package job;

import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import connection.ConnectionPool;
import excetion.CouponSystemException;

public class DailyJob implements Runnable {
	private CouponsDAO couponsDAO;
	private boolean quit;
	private final int daysInMilliseconds = 1000*60*60*24;
	private Thread jobThread;
		
	public DailyJob(CouponsDBDAO couponsDAO) throws CouponSystemException {
		super();
		this.couponsDAO = couponsDAO;
		this.quit = false;
	}
	
	@Override
	public void run() {
		jobThread = Thread.currentThread();
		try {
			System.out.println("CouponExpirationDailyJob run");
			while (!quit) {
				couponsDAO.deleteExpierdCoupons();
				System.out.println("CouponExpirationDailyJob deleteExpierdCoupons");
				Thread.sleep(daysInMilliseconds);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getCause());
		} catch (InterruptedException e) {
			System.out.println("Thread was interupted and will stoped");
		}
		System.out.println("job thread finished");
	}
	public void stop() {
		this.quit = true;
		jobThread.interrupt();
	}
}
