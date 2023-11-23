package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.order.Order;
import com.yaps.petstore.server.domain.orderline.OrderLine;
import com.yaps.petstore.server.domain.product.Product;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the OrderLine class
 */
public final class OrderLineTest extends AbstractTestCase {

    public OrderLineTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(OrderLineTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindOrderLineWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final String id = getUniqueStringId();
        try {
            findOrderLine(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            new OrderLine().findByPrimaryKey(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            new OrderLine().findByPrimaryKey(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllOrderLines() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = findAllOrderLines();

        // Create an object
        final String orderLineId = createOrderLine(id);

        // Ensures that the object exists
        try {
            findOrderLine(orderLineId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllOrderLines();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeOrderLine(orderLineId);

        try {
            findOrderLine(orderLineId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateOrderLine() throws Exception {
        final int id = getUniqueId();
        OrderLine orderLine = null;

        // Creates an object
        final String orderLineId = createOrderLine(id);

        // Ensures that the object exists
        try {
            orderLine = findOrderLine(orderLineId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findOrderLineSql(orderLineId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkOrderLine(orderLine, id);

        // Cleans the test environment
        removeOrderLine(orderLineId);

        try {
            findOrderLine(orderLineId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findOrderLineSql(orderLineId);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateOrderLineWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final OrderLine orderLine = new OrderLine(new String(), 0, 0, null, null);
            orderLine.create();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final OrderLine orderLine = new OrderLine(null, 0, 0, null, null);
            orderLine.create();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testDomainUpdateUnknownOrderLine() throws Exception {
        // Updates this object
        try {
            new OrderLine().update();
            fail("Updating a none existing object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateOrderLineWithInvalidValues() throws Exception {

        // Creates an object
        final int id = getUniqueId();
        final String orderLineId = createOrderLine(id);

        // Ensures that the object exists
        OrderLine orderLine = null;
        try {
            orderLine = findOrderLine(orderLineId);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            orderLine.setQuantity(0);
            orderLine.setUnitCost(0);
            orderLine.update();
            fail();
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            orderLine = findOrderLine(orderLineId);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeOrderLine(orderLineId);

        try {
            findOrderLine(orderLineId);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
        }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDomainDeleteUnknownOrderLine() throws Exception {
        // Delete Order Item
        try {
            new OrderLine().remove();
            fail();
        } catch (CheckException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private OrderLine findOrderLine(final String id) throws FinderException, CheckException {
        final OrderLine orderLine = new OrderLine();
        orderLine.findByPrimaryKey(id);
        return orderLine;
    }

    private void findOrderLineSql(final String id) throws ObjectNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final String sql;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            sql = "SELECT * FROM T_ORDER_LINE WHERE ID = '" + id + "' ";
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

    private int findAllOrderLines() throws FinderException {
        try {
            return new OrderLine().findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first, then a product linked to this category and an item linked to the product
    // Creates a Customer and an order linked to the customer
    // Creates an orderLine linked to the order and the item
    private String createOrderLine(final int id) throws CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        category.create();
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        product.create();
        // Create Item
        final Item item = new Item("item" + id, "name" + id, id, product);
        item.create();

        // Create Customer
        final Customer customer = new Customer("custo" + id, "firstname" + id, "lastname" + id);
        customer.create();
        // Create Order
        final Order order = new Order("firstname" + id, "lastname" + id, "street1" + id, "city" + id, "zip" + id, "country" + id, customer);
        order.setStreet2("street2" + id);
        order.setCreditCardExpiryDate("ccexp" + id);
        order.setCreditCardNumber("ccnum" + id);
        order.setCreditCardType("cctyp" + id);
        order.setState("state" + id);
        order.create();

        // Create OrderLine
        final OrderLine orderLine = new OrderLine(id, id, order, item);
        orderLine.create();
        return orderLine.getId();
    }

    private void checkOrderLine(final OrderLine orderLine, final int id) {
        assertEquals("quantity", id, orderLine.getQuantity());
    }

    private void removeOrderLine(final String orderLineId) throws RemoveException, CheckException {
        final OrderLine orderLine = new OrderLine(orderLineId);
        orderLine.remove();
    }
}
