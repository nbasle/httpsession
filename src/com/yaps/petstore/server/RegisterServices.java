package com.yaps.petstore.server;

import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.common.rmi.RMIConstant;
import com.yaps.petstore.server.service.catalog.CatalogService;
import com.yaps.petstore.server.service.customer.CustomerService;
import com.yaps.petstore.server.service.order.OrderService;

import java.rmi.Naming;

/**
 * This class registers the three RMI services of the Petstore. These services are
 * CatalogService, CustomerService and OrderService. This class has to be launched once
 * on the server side. Without registering, the 3 services would not be accessible
 * from the client.
 *
 * @see CatalogService
 * @see CustomerService
 * @see OrderService
 */
public final class RegisterServices implements RMIConstant {

    // Used for logging
    private static final String _cname = RegisterServices.class.getName();

    public static void main(final String[] args) {
        final String mname = "main";

        try {
            Naming.rebind(CATALOG_SERVICE, new CatalogService());
            Naming.rebind(CUSTOMER_SERVICE, new CustomerService());
            Naming.rebind(ORDER_SERVICE, new OrderService());
            Trace.info(_cname, mname, "Petstore services are registered.");
        } catch (Exception e) {
            Trace.severe(_cname, mname, "Petstore services couldn't be registered");
            Trace.throwing(_cname, mname, e);
        }
    }
}
