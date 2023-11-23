package com.yaps.petstore.server.domain;

import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;
import com.yaps.petstore.server.util.uidgen.UniqueIdGenerator;

import java.io.Serializable;
import java.util.Collection;

/**
 * Persistent object are DomainObjects that can persist their data into a persistent layer.
 * This class implements the Serializable interface. It defines methods to
 * create, remove, update and find values from a persistent layer. An object
 * can use these methods or overwrite them when needed. These methods delegate
 * the perssitent storage to the AbstractDataAccessObject class.
 *
 * @see AbstractDataAccessObject
 */
public abstract class PersistentObject extends DomainObject implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
    protected AbstractDataAccessObject _dao;

    // Every domain object has a unique identifier.
    protected String _id;

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * Given an id this method loads an object attributes from a persistent layer.
     * It calls the AbstractDataAccessObject to select the object from the persistent layer.
     *
     * @param id identifier
     * @throws ObjectNotFoundException thrown if the id is not found
     * @throws FinderException         thrown if a system failure occurs
     * @throws CheckException          is thrown if a invalid data is found
     */
    public final void findByPrimaryKey(final String id) throws FinderException, CheckException {
        final String mname = "findByPrimaryKey";
        Trace.entering(getCname(), mname, id);

        // Checks data integrity
        checkId(id);

        // Uses the DAO to access the persistent layer
        final Object object = _dao.select(id);

        // Loads all the attributes of the curretn object
        loadObject(object);
    }

    /**
     * This methods returns all objects from the system.
     *
     * @return a collection of PersistentObjects
     * @throws ObjectNotFoundException thrown if the id is not found
     */
    public final Collection findAll() throws FinderException {
        final String mname = "findAll";
        Trace.entering(getCname(), mname);

        // Uses the DAO to access the persistent layer
        final Collection objects = _dao.selectAll();

        Trace.exiting(getCname(), mname, new Integer(objects.size()));
        return objects;
    }

    /**
     * When all the object attibutes are set, calling this method will save the object
     * into a persistent layer. This method checks that the mandatory attributes
     * are set and calls the AbstractDataAccessObject to insert the object in the persistent layer.
     *
     * @return PersistentObject After the object is inserted into the persistent layer, it
     *         is returned.
     * @throws DuplicateKeyException is thrown if an object with the same id
     *                               already exists in the system
     * @throws CreateException       is thrown if a mandatory attribute is not set
     *                               or a system failure is occurs
     * @throws CheckException        is thrown if a invalid data is found
     */
    public PersistentObject create() throws CreateException, CheckException {
        final String mname = "create";
        Trace.entering(getCname(), mname);

        // Checks data integrity
        checkData();

        // Uses the DAO to access the persistent layer
        _dao.insert(this);

        return this;
    }

    /**
     * When all the object attibutes are set, calling this method will update the object
     * into a persistent layer. This method checks that the mandatory attributes
     * are set (customer id, first name and last name) and calls the
     * AbstractDataAccessObject to insert the object in the persistent layer.
     *
     * @throws UpdateException is thrown if a mandatory attribute is not set
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public final void update() throws UpdateException, CheckException {
        final String mname = "update";
        Trace.entering(getCname(), mname);

        // Checks data integrity
        checkData();

        try {

            // Uses the DAO to access the persistent layer
            _dao.update(this);

        } catch (ObjectNotFoundException e) {
            throw new UpdateException("Cannot update object. Object not found");
        }
    }

    /**
     * Calling this method will remove the object from the persistent layer.
     * The id cannot be null or empty.
     *
     * @throws RemoveException thrown if the customer id is invalid or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     */
    public final void remove() throws RemoveException, CheckException {
        final String mname = "remove";
        Trace.entering(getCname(), mname);

        // Checks data integrity
        checkId(getId());

        try {

            // Uses the DAO to access the persistent layer
            _dao.remove(getId());

        } catch (ObjectNotFoundException e) {
            throw new RemoveException("Cannot remove the object. Object not found");
        }
    }

    /**
     * This method load all the values of the current object given an object as parameter.
     * This method follows the "Template Method" Design Pattern. It is used by
     * the {@link #findByPrimaryKey(String) findByPrimaryKey} method. Every concrete class must redefine this method.
     *
     * @param object to load from
     */
    protected abstract void loadObject(Object object);

    /**
     * This method checks the integrity of the object data. Because this method is specific to
     * every class, it is abstract.
     * This method follows the "Template Method" Design Pattern. It is used by
     * the {@link #create() create} and {@link #update() update} methods. Every concrete class must redefine this method.
     *
     * @throws CheckException if data is invalide
     */
    protected abstract void checkData() throws CheckException;

    /**
     * This method checks that the identifier is valid.
     *
     * @param id identifier to check
     * @throws CheckException if the id is invalid
     */
    protected static void checkId(final String id) throws CheckException {
        if (id == null || "".equals(id))
            throw new CheckException("Invalid id");
    }

    /**
     * This method returns a unique identifer generated by the system. It is
     * mainly used for Orders and OrderLines
     *
     * @param counterName name of the counter
     * @return a unique identifer
     */
    protected final String getUniqueId(final String counterName) {
        return UniqueIdGenerator.getInstance().getUniqueId(counterName);
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public final String getId() {
        return _id;
    }

    public final void setId(final String id) {
        _id = id;
    }
}
