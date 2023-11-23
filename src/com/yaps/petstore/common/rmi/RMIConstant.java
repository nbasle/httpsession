package com.yaps.petstore.common.rmi;

/**
 * This interface lists all the constants that the system uses to get RMI connection.
 */
public interface RMIConstant {

    /**
     * RMI Host.
     */
    String RMI_HOST = "localhost";
    /**
     * RMI Port.
     */
    int RMI_PORT = 1099;

    /**
     * RMI CatalogService name.
     */
    String CATALOG_SERVICE = "CatalogService";

    /**
     * RMI CustomerService name.
     */
    String CUSTOMER_SERVICE = "CustomerService";

    /**
     * RMI OrderService name.
     */
    String ORDER_SERVICE = "OrderService";

    /**
     * Full RMI CatalogService URL.
     */
    String CATALOG_SERVICE_URL = "//" + RMI_HOST + ":" + RMI_PORT + "/" + CATALOG_SERVICE;

    /**
     * Full RMI CustomerService URL.
     */
    String CUSTOMER_SERVICE_URL = "//" + RMI_HOST + ":" + RMI_PORT + "/" + CUSTOMER_SERVICE;

    /**
     * Full RMI OrderService URL.
     */
    String ORDER_SERVICE_URL = "//" + RMI_HOST + ":" + RMI_PORT + "/" + ORDER_SERVICE;

}
