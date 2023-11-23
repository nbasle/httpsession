package com.yaps.petstore.server.service.order;

import com.yaps.petstore.common.dto.OrderDTO;
import com.yaps.petstore.common.exception.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface gives a remote view of the OrderService. Any distant client that wants to call
 * a method on the OrderService has to use this interface. Because it extends the Remote interface,
 * every method must throw RemoteException.
 */
public interface OrderServiceRemote extends Remote {

    // ======================================
    // =           Business methods         =
    // ======================================

    /**
     * Given a OrderDTO object, this method creates a Order. It first transforms
     * a OrderDTO into a Order domain object, uses the Order object to apply the
     * business rules for creation. It then transforms back the Order object into a
     * OrderDTO.
     *
     * @param orderDTO cannot be null.
     * @return the created OrderDTO
     * @throws CreateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    OrderDTO createOrder(OrderDTO orderDTO) throws CreateException, CheckException, RemoteException;

    /**
     * Given an id this method uses the Order domain object to load all the data of this
     * object. It then transforms this object into a OrderDTO and returns it.
     *
     * @param orderId identifier
     * @return OrderDTO
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     * @throws RemoteException         is thrown if a remote call fails
     */
    OrderDTO findOrder(String orderId) throws FinderException, CheckException, RemoteException;

    /**
     * Given an id, this method finds an Order domain object and then calls its deletion
     * method.
     *
     * @param orderId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void deleteOrder(String orderId) throws RemoveException, CheckException, RemoteException;
}
