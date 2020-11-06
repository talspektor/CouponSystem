package tests;


import DAO.CustomersDBDAO;
import beans.Customer;
import excetion.CouponSystemException;

public class TestDAO {

	public static void main(String[] args) {
		
		try {
			CustomersDBDAO custometDAO = new CustomersDBDAO();
			Customer customer =  new Customer("tal", "spektor", "tal@spektor", "password");
			custometDAO.addCustomer(customer);
			
			
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
