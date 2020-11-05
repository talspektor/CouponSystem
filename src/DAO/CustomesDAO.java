package DAO;

import java.util.ArrayList;

import beans.Customer;
import excetion.CouponSystemException;

public interface CustomesDAO {
	public boolean isCustomerExists(String email, String password) throws CouponSystemException;
	public void addCustomer(Customer customer) throws CouponSystemException;
	public void updateCustomer(Customer customer) throws CouponSystemException;
	public void deleteCustomer(int customerId) throws CouponSystemException;
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException;
	public Customer getOneCustomer(int customerId) throws CouponSystemException;
}
