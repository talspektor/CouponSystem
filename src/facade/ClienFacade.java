package facade;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;
import excetion.CouponSystemException;

public abstract class ClienFacade {
	protected CustomesDAO customerDAO;
	protected CompaniesDAO companiesDAO;
	protected CouponsDAO couponsDAO;
	public ClienFacade(CustomesDAO customerDAO, CompaniesDAO companiesDAO, CouponsDAO couponsDAO) {
		super();
		this.customerDAO = customerDAO;
		this.companiesDAO = companiesDAO;
		this.couponsDAO = couponsDAO;
	}
	public abstract boolean login(String email, String password) throws CouponSystemException;
}
