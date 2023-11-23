package com.yaps.petstore.server.service.customer;

import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * This interface gives a remote view of the CustomerService. Any distant client that wants to call
 * a method on the CustomerService has to use this interface. Because it extends the Remote interface,
 * every method must throw RemoteException.
 */
public interface CustomerServiceRemote extends Remote {

    // ======================================
    // =           Business methods         =
    // ======================================

    /**
     * Given an id and password, this method checks if the password is the right one for
     * this customer identifier. If it matches, the method returns a CustomerDTO with
     * all the customer data.
     *
     * @param customerId
     * @param password   to be checked if it's the right one
     * @return CustomerDTO customer data
     * @throws FinderException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data or password is found
     * @throws RemoteException is thrown if a remote call fails
     */
    CustomerDTO authenticate(String customerId, String password) throws FinderException, CheckException, RemoteException;

    /**
     * Given a CustomerDTO object, this method creates a Customer. It first transforms
     * a CustomerDTO into a Customer domain object, uses the Customer object to apply the
     * business rules for creation. It then transforms back the Customer object into a
     * CustomerDTO.
     *
     * @param customerDTO cannot be null.
     * @return the created CustomerDTO
     * @throws DuplicateKeyException is thrown if an object with the same id
     *                               already exists in the system
     * @throws CreateException       is thrown if a DomainException is caught
     *                               or a system failure is occurs
     * @throws CheckException        is thrown if a invalid data is found
     * @throws RemoteException       is thrown if a remote call fails
     */
    CustomerDTO createCustomer(CustomerDTO customerDTO) throws CreateException, CheckException, RemoteException;

    /**
     * Given an id this method uses the Customer domain object to load all the data of this
     * object. It then transforms this object into a CustomerDTO and returns it.
     *
     * @param customerId identifier
     * @return CustomerDTO
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     * @throws RemoteException         is thrown if a remote call fails
     */
    CustomerDTO findCustomer(String customerId) throws FinderException, CheckException, RemoteException;

    /**
     * Given an id, this method finds a Customer domain object and then calls its deletion
     * method.
     *
     * @param customerId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void deleteCustomer(String customerId) throws RemoveException, CheckException, RemoteException;

    /**
     * Given a CustomerDTO object, this method updates a Customer. It first transforms
     * a CustomerDTO into a Customer domain object and uses the Customer object to apply the
     * business rules for modification.
     *
     * @param customerDTO cannot be null.
     * @throws UpdateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void updateCustomer(CustomerDTO customerDTO) throws UpdateException, CheckException, RemoteException;

    /**
     * This method return all the customers from the system. It uses the Customer domain object
     * to get the data. It then transforms this collection of Customer object into a
     * collection of CustomerDTO and returns it.
     *
     * @return a collection of CustomerDTO
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws RemoteException         is thrown if a remote call fails
     */
    Collection findCustomers() throws FinderException, RemoteException;
}
