package com.yaps.petstore.server.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * A service is a class that follows the Facade Design Pattern. It gives a set of services
 * to remote or local client. Every service class should extend this class.
 */
public abstract class AbstractRemoteService extends UnicastRemoteObject {

    // ======================================
    // =             Attributes             =
    // ======================================

    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // ======================================
    // =            Constructors            =
    // ======================================
    protected AbstractRemoteService() throws RemoteException {
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    protected final String getCname() {
        return _cname;
    }
}
