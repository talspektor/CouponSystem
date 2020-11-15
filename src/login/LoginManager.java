package login;

import DAO.CompaniesDBDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import excetion.CouponSystemException;
import facade.AdminFacade;
import facade.ClienFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

/**
 * @author tals
 * singleton
 */
public class LoginManager {
	
	private static LoginManager instance = new LoginManager();
	
	private LoginManager() {
		
	}
	
	public static LoginManager getInstance() {
		return  instance;
	}
	
	/**
	 * @param email
	 * @param password
	 * @param clientType
	 * @return the facade
	 * @throws CouponSystemException
	 */
	public ClienFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
		switch (clientType) {
		case ADMINISTRATOR:
			return new AdminFacade(new CustomersDBDAO(), new CompaniesDBDAO(), new CouponsDBDAO());
		case COMPNY:
			return new CompanyFacade(new CustomersDBDAO(), new CompaniesDBDAO(), new CouponsDBDAO());
		case CUSTOMER:
			return new CustomerFacade(new CustomersDBDAO(), new CompaniesDBDAO(), new CouponsDBDAO());
		}
		return null;
	}
}
