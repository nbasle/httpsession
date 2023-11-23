package com.yaps.petstore.common.delegate;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.rmi.RMIConstant;
import com.yaps.petstore.server.service.customer.CustomerService;
import junit.framework.TestSuite;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * This class tests the CatalogService class
 */
public final class CustomerDelegateTest extends AbstractTestCase {

    public CustomerDelegateTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CustomerDelegateTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDelegateFindCustomerWithInvalidValues() throws Exception {

        // Binds the RMI service
        bindService();

        // Finds an object with a unknown identifier
        final String id = getUniqueStringId();
        try {
            CustomerDelegate.findCustomer(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            CustomerDelegate.findCustomer(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            CustomerDelegate.findCustomer(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test authenticates a customer.
     */
    public void testDelegateAuthenticateCustomer() throws Exception {
        final String id = getUniqueStringId();
        CustomerDTO customerDTO = null;

        // Ensures that the object doesn't exist
        try {
            findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customerDTO = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customerDTO, id);

        // Authenticating a customer with invalid password
        try {
            customerDTO = CustomerDelegate.authenticate("custo" + id, "invalid pwd" + id);
            fail("Invalid password, object shouldn't be found");
        } catch (CheckException e) {
        }

        // Authenticating a customer should find a customer with id and password
        try {
            customerDTO = CustomerDelegate.authenticate("custo" + id, "pwd" + id);
        } catch (FinderException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customerDTO, id);

        // Cleans the test environment
        deleteCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDelegateFindAllCustomers() throws Exception {
        final String id = getUniqueStringId();

        // First findAll
        final int firstSize = findAllCustomers();

        // Creates an object
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
        deleteCustomer(id);

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
    public void testDelegateCreateCustomer() throws Exception {
        final String id = getUniqueStringId();
        CustomerDTO customerDTO = null;

        // Ensures that the object doesn't exist
        try {
            findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customerDTO = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customerDTO, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCustomer(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDelegateCreateCustomerWithInvalidValues() throws Exception {
        CustomerDTO customerDTO;

        // Binds the RMI service
        bindService();

        // Creates an object with a null parameter
        try {
            CustomerDelegate.createCustomer(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            customerDTO = new CustomerDTO(new String(), new String(), new String());
            CustomerDelegate.createCustomer(customerDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            customerDTO = new CustomerDTO(null, null, null);
            CustomerDelegate.createCustomer(customerDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This method ensures that creating an object with invalid credit card information
     * doesn't work.
     */
    public void testDelegateCreateCustomerWithInvalidCreditCard() throws Exception {
        final String id = getUniqueStringId();
        CustomerDTO customerDTO = new CustomerDTO("custo" + id, "firstname" + id, "lastname" + id);

        // Binds the RMI service
        bindService();

        // Invalid credit card date
        customerDTO.setCreditCardExpiryDate("10/02");
        customerDTO.setCreditCardNumber("4564 1231 4564 2222");
        customerDTO.setCreditCardType("Visa");
        try {
            CustomerDelegate.createCustomer(customerDTO);
            fail("Credit card date was invalid. Object shouldn't be created");
        } catch (CheckException e) {
        }

        // Invalid credit card number for a visa
        customerDTO.setCreditCardExpiryDate("10/08");
        customerDTO.setCreditCardNumber("4564 1231 4564 1111");
        customerDTO.setCreditCardType("Visa");
        try {
            CustomerDelegate.createCustomer(customerDTO);
            fail("Credit card number was invalid. Object shouldn't be created");
        } catch (CheckException e) {
        }

        // The client doesn't pay with the credit card but with a cheque
        customerDTO.setCreditCardExpiryDate("");
        customerDTO.setCreditCardNumber("");
        customerDTO.setCreditCardType("");
        try {
            CustomerDelegate.createCustomer(customerDTO);
        } catch (CreateException e) {
            fail("Credit card wasn't used. Object should be created");
        }

        // Ensures that the object exists
        try {
            customerDTO = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Cleans the test environment
        deleteCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDelegateUpdateCustomer() throws Exception {
        final String id = getUniqueStringId();
        final String updatedId = getUniqueStringId();

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        CustomerDTO customerDTO = null;
        try {
            customerDTO = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customerDTO, id);

        // Updates the object with new values
        updateCustomer(customerDTO, updatedId);

        // Ensures that the object still exists
        CustomerDTO customerUpdated = null;
        try {
            customerUpdated = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCustomer(customerUpdated, updatedId);

        // Cleans the test environment
        deleteCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDelegateUpdateCustomerWithInvalidValues() throws Exception {
        CustomerDTO customerDTO;

        // Binds the RMI service
        bindService();

        // Updates an object with a null parameter
        try {
            CustomerDelegate.updateCustomer(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
            customerDTO = new CustomerDTO(new String(), new String(), new String());
            CustomerDelegate.updateCustomer(customerDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            customerDTO = new CustomerDTO(null, null, null);
            CustomerDelegate.updateCustomer(customerDTO);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

    /**
     * This method ensures that updating an object with invalid credit card information
     * doesn't work.
     */
    public void testDelegateUpdateCustomerWithInvalidCreditCard() throws Exception {
        final String id = getUniqueStringId();

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        CustomerDTO customerDTO = null;
        try {
            customerDTO = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customerDTO, id);

        // Invalid credit card date
        customerDTO.setCreditCardExpiryDate("10/02");
        customerDTO.setCreditCardNumber("4564 1231 4564 2222");
        customerDTO.setCreditCardType("Visa");
        try {
            CustomerDelegate.updateCustomer(customerDTO);
            fail("Credit card date was invalid. Object shouldn't be updated");
        } catch (CheckException e) {
        }

        // Invalid credit card number for a visa
        customerDTO.setCreditCardExpiryDate("10/08");
        customerDTO.setCreditCardNumber("4564 1231 4564 1111");
        customerDTO.setCreditCardType("Visa");
        try {
            CustomerDelegate.updateCustomer(customerDTO);
            fail("Credit card number was invalid. Object shouldn't be updated");
        } catch (CheckException e) {
        }

        // The client doesn't pay with the credit card but with a cheque
        customerDTO.setCreditCardExpiryDate("");
        customerDTO.setCreditCardNumber("");
        customerDTO.setCreditCardType("");
        try {
        CustomerDelegate.updateCustomer(customerDTO);
        } catch (UpdateException e) {
            fail("Credit card wasn't used. Object should be updated");
        }

        // Ensures that the object exists
        try {
            customerDTO = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been updated it should be found");
        }

        // Cleans the test environment
        deleteCustomer(id);

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDelegateDeleteUnknownCustomer() throws Exception {
        final String id = getUniqueStringId();

        // Ensures that the object doesn't exist
        try {
            findCustomer(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteCustomer(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }

    //==================================
    //=          Private Methods       =
    //==================================
    private CustomerDTO findCustomer(final String id) throws FinderException, CheckException {
        CustomerDTO customerDTO = null;

        // Binds the RMI service
        bindService();

        try {
            customerDTO = CustomerDelegate.findCustomer("custo" + id);
        } catch (RemoteException e) {
            fail("Service is bound. Call should have succed");
        }

        // Unbinds the RMI service
        unbindService();

        try {
            customerDTO = CustomerDelegate.findCustomer("custo" + id);
            fail("Service is not bound. Call should have failed");
        } catch (RemoteException e) {
        }

        // Binds the RMI service
        bindService();

        return customerDTO;
    }

    private int findAllCustomers() throws FinderException, RemoteException {
        // Binds the RMI service
        bindService();

        try {
            return CustomerDelegate.findCustomers().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private void createCustomer(final String id) throws CreateException, CheckException {
        // Binds the RMI service
        bindService();

        final CustomerDTO customerDTO = new CustomerDTO("custo" + id, "firstname" + id, "lastname" + id);
        customerDTO.setPassword("pwd" + id);
        customerDTO.setCity("city" + id);
        customerDTO.setCountry("" + id);
        customerDTO.setState("" + id);
        customerDTO.setStreet1("street1" + id);
        customerDTO.setStreet2("street2" + id);
        customerDTO.setTelephone("phone" + id);
        customerDTO.setEmail("email" + id);
        customerDTO.setZipcode("zip" + id);
        customerDTO.setCreditCardExpiryDate("10/08");
        customerDTO.setCreditCardNumber("4564 1231 4564 1222");
        customerDTO.setCreditCardType("Visa");

        try {
            CustomerDelegate.createCustomer(customerDTO);
        } catch (RemoteException e) {
            fail("Service is bound. Call should have succed");
        }

        // Unbinds the RMI service
        unbindService();

        try {
            CustomerDelegate.createCustomer(customerDTO);
            fail("Service is not bound. Call should have failed");
        } catch (RemoteException e) {
        }

        // Binds the RMI service
        bindService();
    }

    private void updateCustomer(final CustomerDTO customerDTO, final String id) throws UpdateException, CheckException, RemoteException {
        // Binds the RMI service
        bindService();

        customerDTO.setFirstname("firstname" + id);
        customerDTO.setLastname("lastname" + id);
        customerDTO.setPassword("pwd" + id);
        customerDTO.setCity("city" + id);
        customerDTO.setCountry("" + id);
        customerDTO.setState("" + id);
        customerDTO.setStreet1("street1" + id);
        customerDTO.setStreet2("street2" + id);
        customerDTO.setTelephone("phone" + id);
        customerDTO.setEmail("email" + id);
        customerDTO.setZipcode("zip" + id);
        customerDTO.setCreditCardExpiryDate("10/08");
        customerDTO.setCreditCardNumber("4564 1231 4564 1222");
        customerDTO.setCreditCardType("Visa");

        CustomerDelegate.updateCustomer(customerDTO);
    }

    private void deleteCustomer(final String id) throws RemoveException, CheckException, RemoteException {
        // Binds the RMI service
        bindService();

        CustomerDelegate.deleteCustomer("custo" + id);
    }

    private void checkCustomer(final CustomerDTO customerDTO, final String id) {
        assertEquals("firstname", "firstname" + id, customerDTO.getFirstname());
        assertEquals("lastname", "lastname" + id, customerDTO.getLastname());
        assertEquals("password", "pwd" + id, customerDTO.getPassword());
        assertEquals("city", "city" + id, customerDTO.getCity());
        assertEquals("country", "" + id, customerDTO.getCountry());
        assertEquals("state", "" + id, customerDTO.getState());
        assertEquals("street1", "street1" + id, customerDTO.getStreet1());
        assertEquals("street2", "street2" + id, customerDTO.getStreet2());
        assertEquals("telephone", "phone" + id, customerDTO.getTelephone());
        assertEquals("email", "email" + id, customerDTO.getEmail());
        assertEquals("zipcode", "zip" + id, customerDTO.getZipcode());
        assertEquals("CreditCardExpiryDate", "10/08", customerDTO.getCreditCardExpiryDate());
        assertEquals("CreditCardNumber", "4564 1231 4564 1222", customerDTO.getCreditCardNumber());
        assertEquals("CreditCardType", "Visa", customerDTO.getCreditCardType());
    }

    private void bindService() {
        try {
            Naming.rebind(RMIConstant.CUSTOMER_SERVICE, new CustomerService());
        } catch (Exception e) {
            fail("Could not bind a service");
        }
    }

    private void unbindService() {
        try {
            Naming.unbind(RMIConstant.CUSTOMER_SERVICE);
        } catch (Exception e) {
            fail("Could not unbind a service");
        }
    }
}
