package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.server.domain.customer.Customer;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the Customer class
 */
public final class CustomerTest extends AbstractTestCase {

    public CustomerTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CustomerTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindCustomerWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = getUniqueId();
        try {
            findCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            new Customer().findByPrimaryKey(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            new Customer().findByPrimaryKey(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllCustomers() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = findAllCustomers();

        // Create an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllCustomers();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateCustomer() throws Exception {
        final int id = getUniqueId();
        Customer customer = null;

        // Ensures that the object doesn't exist
        try {
            customer = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findCustomerSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCustomer(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findCustomerSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateCustomerWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Customer customer = new Customer(new String(), new String(), new String());
            customer.create();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Customer customer = new Customer(null, null, null);
            customer.create();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This method ensures that the method for matching a password works.
     */
    public void testDomainMatchPasswordCustomer() throws Exception {
        final int id = getUniqueId();
        Customer customer = null;

        // Ensures that the object doesn't exist
        try {
            customer = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Match invalid password
        try {
            customer.matchPassword("invalid password");
            fail("Invalid password shouldn't match");
        } catch (CheckException e) {
        }

        // Match valid password
        try {
            customer.matchPassword("pass" + id);
        } catch (CheckException e) {
            fail("Valid password should match");
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testDomainUpdateUnknownCustomer() throws Exception {
        // Updates an unknown object
        try {
            new Customer().update();
            fail("Updating a none existing object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateCustomerWithInvalidValues() throws Exception {

        // Creates an object
        final int id = getUniqueId();
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            customer.setFirstname(new String());
            customer.setLastname(new String());
            customer.update();
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            customer.setFirstname(null);
            customer.setLastname(null);
            customer.update();
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateCustomer() throws Exception {
        final int id = getUniqueId();

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        Customer customer = null;
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findCustomerSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Updates the object with new values
        updateCustomer(customer, id + 1);

        // Ensures that the object still exists
        Customer customerUpdated = null;
        try {
            customerUpdated = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCustomer(customerUpdated, id + 1);

        // Cleans the test environment
        removeCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findCustomerSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDomainDeleteUnknownCustomer() throws Exception {
        // Removes an unknown object
        try {
            new Customer().remove();
            fail("Deleting an unknown object should break");
        } catch (CheckException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Customer findCustomer(final int id) throws FinderException, CheckException {
        final Customer customer = new Customer();
        customer.findByPrimaryKey("custo" + id);
        return customer;
    }

    private void findCustomerSql(final int id) throws ObjectNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final String sql;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            sql = "SELECT * FROM T_CUSTOMER WHERE ID = 'custo" + id + "' ";
            resultSet = statement.executeQuery(sql);
            if (resultSet.next() == false)
                throw new ObjectNotFoundException();

        } finally {
            // Close
            resultSet.close();
            statement.close();
            connection.close();
        }
    }

    private int findAllCustomers() throws FinderException {
        try {
            return new Customer().findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private void createCustomer(final int id) throws CreateException, CheckException {
        final Customer customer = new Customer("custo" + id, "firstname" + id, "lastname" + id);
        customer.setPassword("pass" + id);
        customer.setCity("city" + id);
        customer.setCountry("cnty" + id);
        customer.setState("state" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setTelephone("phone" + id);
        customer.setEmail("email" + id);
        customer.setZipcode("zip" + id);
        customer.setCreditCardExpiryDate("ccexp" + id);
        customer.setCreditCardNumber("ccnum" + id);
        customer.setCreditCardType("cctyp" + id);
        customer.create();
    }

    private void updateCustomer(final Customer customer, final int id) throws UpdateException, CheckException {
        customer.setFirstname("firstname" + id);
        customer.setLastname("lastname" + id);
        customer.setPassword("pass" + id);
        customer.setCity("city" + id);
        customer.setCountry("cnty" + id);
        customer.setState("state" + id);
        customer.setStreet1("street1" + id);
        customer.setStreet2("street2" + id);
        customer.setTelephone("phone" + id);
        customer.setEmail("email" + id);
        customer.setZipcode("zip" + id);
        customer.setCreditCardExpiryDate("ccexp" + id);
        customer.setCreditCardNumber("ccnum" + id);
        customer.setCreditCardType("cctyp" + id);
        customer.update();
    }

    private void removeCustomer(final int id) throws CheckException, RemoveException {
        final Customer customer = new Customer("custo" + id);
        customer.remove();
    }

    private void checkCustomer(final Customer customer, final int id) {
        assertEquals("firstname", "firstname" + id, customer.getFirstname());
        assertEquals("lastname", "lastname" + id, customer.getLastname());
        assertEquals("password", "pass" + id, customer.getPassword());
        assertEquals("city", "city" + id, customer.getCity());
        assertEquals("country", "cnty" + id, customer.getCountry());
        assertEquals("state", "state" + id, customer.getState());
        assertEquals("street1", "street1" + id, customer.getStreet1());
        assertEquals("street2", "street2" + id, customer.getStreet2());
        assertEquals("telephone", "phone" + id, customer.getTelephone());
        assertEquals("email", "email" + id, customer.getEmail());
        assertEquals("zipcode", "zip" + id, customer.getZipcode());
        assertEquals("CreditCardExpiryDate", "ccexp" + id, customer.getCreditCardExpiryDate());
        assertEquals("CreditCardNumber", "ccnum" + id, customer.getCreditCardNumber());
        assertEquals("CreditCardType", "cctyp" + id, customer.getCreditCardType());
    }
}
