package com.yaps.petstore.common.delegate;

import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.rmi.RMIConstant;
import com.yaps.petstore.server.service.customer.CustomerServiceRemote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * This class follows the Delegate design pattern. It's a one to one method
 * with the CustomerService class. Each method delegates the call to the
 * CustomerService class
 */
public final class CustomerDelegate implements RMIConstant {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static CustomerServiceRemote customerServiceRemote;

    /** @link dependency 
     * @label delegates*/
    /*# com.yaps.petstore.server.service.customer.CustomerService lnkCustomerService; */
    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * Delegates the call to the {@link CustomerServiceRemote#authenticate(String, String) CustomerServiceRemote().authenticate} method.
     */
    public static CustomerDTO authenticate(final String customerId, final String password) throws FinderException, CheckException, RemoteException {
        return getCustomerService().authenticate(customerId, password);
    }

    /**
     * Delegates the call to the {@link CustomerServiceRemote#createCustomer(CustomerDTO) CustomerServiceRemote().createCustomer} method.
     */
    public static CustomerDTO createCustomer(final CustomerDTO customerDTO) throws CreateException, CheckException, RemoteException {
        return getCustomerService().createCustomer(customerDTO);
    }

    /**
     * Delegates the call to the {@link CustomerServiceRemote#findCustomer(String) CustomerServiceRemote().findCustomer} method.
     */
    public static CustomerDTO findCustomer(final String customerId) throws FinderException, CheckException, RemoteException {
        return getCustomerService().findCustomer(customerId);
    }

    /**
     * Delegates the call to the {@link CustomerServiceRemote#deleteCustomer(String) CustomerServiceRemote().deleteCustomer} method.
     */
    public static void deleteCustomer(final String customerId) throws RemoveException, CheckException, RemoteException {
        getCustomerService().deleteCustomer(customerId);
    }

    /**
     * Delegates the call to the {@link CustomerServiceRemote#updateCustomer(CustomerDTO) CustomerServiceRemote().updateCustomer} method.
     */
    public static void updateCustomer(final CustomerDTO customerDTO) throws UpdateException, CheckException, RemoteException {
        getCustomerService().updateCustomer(customerDTO);
    }

    /**
     * Delegates the call to the {@link CustomerServiceRemote#findCustomers() CustomerServiceRemote().findCustomers} method.
     */
    public static Collection findCustomers() throws FinderException, RemoteException {
        return getCustomerService().findCustomers();
    }

    // ======================================
    // =            Private methods         =
    // ======================================
    private static CustomerServiceRemote getCustomerService() throws RemoteException {
        try {
            customerServiceRemote = (CustomerServiceRemote) Naming.lookup(CUSTOMER_SERVICE_URL);
        } catch (Exception e) {
            throw new RemoteException("Lookup exception", e);
        }
        return customerServiceRemote;
    }
}
