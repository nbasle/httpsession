package com.yaps.petstore.common.delegate;

import com.yaps.petstore.common.dto.OrderDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.CreateException;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.exception.RemoveException;
import com.yaps.petstore.common.rmi.RMIConstant;
import com.yaps.petstore.server.service.order.OrderServiceRemote;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * This class follows the Delegate design pattern. It's a one to one method
 * with the OrderService class. Each method delegates the call to the
 * OrderService class
 */
public final class OrderDelegate implements RMIConstant {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static OrderServiceRemote orderServiceRemote;

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * Delegates the call to the {@link OrderServiceRemote#createOrder(OrderDTO) OrderServiceRemote().createOrder} method.
     */
    public static OrderDTO createOrder(final OrderDTO orderDTO) throws CreateException, CheckException, RemoteException {
        return getOrderService().createOrder(orderDTO);
    }

    /**
     * Delegates the call to the {@link OrderServiceRemote#findOrder(String) OrderServiceRemote().findOrder} method.
     */
    public static OrderDTO findOrder(final String orderId) throws FinderException, CheckException, RemoteException {
        return getOrderService().findOrder(orderId);
    }

    /**
     * Delegates the call to the {@link OrderServiceRemote#deleteOrder(String) OrderServiceRemote().deleteOrder} method.
     */
    public static void deleteOrder(final String orderId) throws RemoveException, CheckException, RemoteException {
        getOrderService().deleteOrder(orderId);
    }

    // ======================================
    // =            Private methods         =
    // ======================================
    private static OrderServiceRemote getOrderService() throws RemoteException {
        try {
            orderServiceRemote = (OrderServiceRemote) Naming.lookup(ORDER_SERVICE_URL);
        } catch (Exception e) {
            throw new RemoteException("Lookup exception", e);
        }
        return orderServiceRemote;
    }
}
