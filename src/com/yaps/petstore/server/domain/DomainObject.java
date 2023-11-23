package com.yaps.petstore.server.domain;

/**
 * Every domain object should extend this abstract class.
 */
public abstract class DomainObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    protected String getCname() {
        return _cname;
    }
}
