package facade;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomesDAO;

public abstract class ClienFacade {
	CustomesDAO customerDAO;
	CompaniesDAO companiesDAO;
	CouponsDAO couponsDAO;
}
