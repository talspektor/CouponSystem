package tests;


import DAO.CustomersDBDAO;
import beans.Customer;
import excetion.CouponSystemException;

public class TestDAO {

	public static void main(String[] args) {
		
		try {
			CustomersDBDAO custometDAO = new CustomersDBDAO();
			String email = "tal@spektor99";
			String password = "password";
			Customer customer =  new Customer("ethan", "spektor", email, password);
			customer.setId(1);
//			custometDAO.addCustomer(customer);
//			System.out.println(custometDAO.isCustomerExists(email, password)); 
//			custometDAO.updateCustomer(customer);
//			custometDAO.deleteCustomer(2);
//			System.out.println(custometDAO.getAllCustomers());
			System.out.println(custometDAO.getOneCustomer(3));
					
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
