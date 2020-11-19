package ShowProgramPerformance;

import excetion.CouponSystemException;

public class Progrem {

	public static void main(String[] args) {
		Test test = new Test();
		try {
			test.testAll();
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}	
	}
}
