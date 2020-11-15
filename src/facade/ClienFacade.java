package facade;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import excetion.CouponSystemException;

public abstract class ClienFacade {
	CustomesDAO customerDAO;
	CompaniesDAO companiesDAO;
	CouponsDAO couponsDAO;
	public abstract boolean login(String email, String password) throws CouponSystemException;
}
