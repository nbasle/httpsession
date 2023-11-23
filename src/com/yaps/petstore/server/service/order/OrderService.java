package com.yaps.petstore.server.service.order;

import com.yaps.petstore.common.dto.OrderDTO;
import com.yaps.petstore.common.dto.OrderLineDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.CreateException;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.exception.RemoveException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.order.Order;
import com.yaps.petstore.server.domain.orderline.OrderLine;
import com.yaps.petstore.server.service.AbstractRemoteService;
import com.yaps.petstore.server.service.creditcard.CreditCardService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class is a facade for all order services.
 */
public final class OrderService extends AbstractRemoteService implements OrderServiceRemote {

    // ======================================
    // =            Constructors            =
    // ======================================
    public OrderService() throws RemoteException {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public OrderDTO createOrder(final OrderDTO orderDTO) throws CreateException, CheckException {
        final String mname = "createOrder";
        Trace.entering(getCname(), mname, orderDTO);

        if (orderDTO == null)
            throw new CreateException("Order object is null");

        if (orderDTO.getOrderLines() == null || orderDTO.getOrderLines().size() < 0)
            throw new CheckException("There are no order lines");

            // Finds the customer
        final Customer customer = new Customer();
        try {
            customer.findByPrimaryKey(orderDTO.getCustomerId());
        } catch (FinderException e) {
            throw new CreateException("Customer must exist to create an order");
        }

        // Transforms Order DTO into domain object
        final Order order = new Order(orderDTO.getFirstname(), orderDTO.getLastname(),
                orderDTO.getStreet1(), orderDTO.getCity(),
                orderDTO.getZipcode(), orderDTO.getCountry(), customer);
        order.setStreet2(orderDTO.getStreet2());
        order.setState(orderDTO.getState());
        order.setCreditCardExpiryDate(orderDTO.getCreditCardExpiryDate());
        order.setCreditCardNumber(orderDTO.getCreditCardNumber());
        order.setCreditCardType(orderDTO.getCreditCardType());

            // Checks if the credit card is valid
            getCreditCardService().verifyCreditCard(order.getCreditCard());

        // Creates the order
            order.create();

            // Creates all the orderLines linked with the order
        Collection orderLines = new ArrayList();
            for (Iterator iterator = orderDTO.getOrderLines().iterator(); iterator.hasNext();) {
            final OrderLineDTO orderLineDTO = (OrderLineDTO) iterator.next();
            // Finds the item
            final Item item = new Item();
            try {
                item.findByPrimaryKey(orderLineDTO.getItemId());
        } catch (FinderException e) {
                throw new CreateException("Item must exist to create an order line");
        }
            // Transforms OrderLine DTO into domain object
            final OrderLine orderLine = new OrderLine(orderLineDTO.getQuantity(), orderLineDTO.getUnitCost(), order, item);
            // Creates the order line
            orderLine.create();
            orderLines.add(orderLine);
        }
        // Sets orderLines into the order
        order.setOrderLines(orderLines);

        // Transforms domain object into DTO
        final OrderDTO result = transformOrder2DTO(order);
        return result;
    }

    public OrderDTO findOrder(final String orderId) throws FinderException, CheckException {
        final String mname = "findOrder";
        Trace.entering(getCname(), mname, orderId);

        final Order order = new Order();
        final Customer customer = new Customer();

        // Finds the object
        order.findByPrimaryKey(orderId);

        // Retreives the data for the customer and sets it
        customer.findByPrimaryKey(order.getCustomer().getId());
        order.setCustomer(customer);

        // Retreives the data for all the order lines
        final Collection orderLines = new OrderLine().findAll(orderId);
        order.setOrderLines(orderLines);

        // Transforms domain object into DTO
        final OrderDTO orderDTO = transformOrder2DTO(order);

        Trace.exiting(getCname(), mname, orderDTO);
        return orderDTO;
    }

    public void deleteOrder(final String orderId) throws RemoveException, CheckException {
        final String mname = "deleteOrder";
        Trace.entering(getCname(), mname, orderId);

        final Order order = new Order();

            // Checks if the object exists
        try {
            order.findByPrimaryKey(orderId);
        } catch (FinderException e) {
            throw new RemoveException("Order must exist to be deleted");
        }

        // Deletes the object
        order.remove();
    }

    // ======================================
    // =          Private Methods           =
    // ======================================
    private OrderDTO transformOrder2DTO(final Order order) {
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCity(order.getCity());
        orderDTO.setCountry(order.getCountry());
        orderDTO.setCreditCardExpiryDate(order.getCreditCardExpiryDate());
        orderDTO.setCreditCardNumber(order.getCreditCardNumber());
        orderDTO.setCreditCardType(order.getCreditCardType());
        orderDTO.setCustomerId(order.getCustomer().getId());
        orderDTO.setFirstname(order.getFirstname());
        orderDTO.setLastname(order.getLastname());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setState(order.getState());
        orderDTO.setStreet1(order.getStreet1());
        orderDTO.setStreet2(order.getStreet2());
        orderDTO.setZipcode(order.getZipcode());
        // Transforms all the order lines
        orderDTO.setOrderLines(transformOrderLines2DTOs(order.getOrderLines()));
        return orderDTO;
    }

    private Collection transformOrderLines2DTOs(final Collection orderLines) {
        final Collection orderLinesDTO = new ArrayList();
        OrderLineDTO orderLineDTO;
        for (Iterator iterator = orderLines.iterator(); iterator.hasNext();) {
            final OrderLine orderLine = (OrderLine) iterator.next();
            orderLineDTO = new OrderLineDTO();
        orderLineDTO.setItemId(orderLine.getItem().getId());
        orderLineDTO.setItemName(orderLine.getItem().getName());
            orderLineDTO.setQuantity(orderLine.getQuantity());
            orderLineDTO.setUnitCost(orderLine.getUnitCost());
            orderLinesDTO.add(orderLineDTO);
    }
        return orderLinesDTO;
    }

    private CreditCardService getCreditCardService() {
        return new CreditCardService();
    }
}
