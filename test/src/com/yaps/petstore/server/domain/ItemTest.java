package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.product.Product;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the Product class
 */
public final class ItemTest extends AbstractTestCase {

    public ItemTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(ItemTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindItemWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = getUniqueId();
        try {
            findItem(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            new Item().findByPrimaryKey(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            new Item().findByPrimaryKey(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllItems() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = findAllItems();

        // Create an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllItems();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllItemsForAProduct() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = findAllItems("prod" + id);

        // Checks that the collection is empty
        if (firstSize != 0) fail("The collection should be empty");

        // Create an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllItems("prod" + id);

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Create an new object with a different id
        createItem(id + 1);

        // Ensures that the new object exists
        try {
            findItem(id + 1);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // third findAll
        final int thirdSize = findAllItems("prod" + id);

        // Checks that the collection size is the same
        // Because the object created had a different id, it should not be in the collection
        if (thirdSize != secondSize) fail("The collection should have the same size");

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the method search works. It does a first search, creates
     * a new object and does a second search.
     */
    public void testDomainSearchItems() throws Exception {
        final int id = getUniqueId();

        // First search
        final int firstSize = searchItems(id);

        // Create an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second search
        final int secondSize = searchItems(id);

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Create an new object with a different id
        createItem(id + 1);

        // Ensures that the new object exists
        try {
            findItem(id + 1);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // third search
        final int thirdSize = searchItems(id);

        // Checks that the collection size is the same
        // Because the object created had a different id, it should not be in the collection
        if (thirdSize != secondSize) fail("The collection should have the same size");

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateItem() throws Exception {
        final int id = getUniqueId();
        Item item = null;

        // Ensures that the object doesn't exist
        try {
            item = findItem(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findItemSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkItem(item, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createItem(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findItemSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateItemWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Item item = new Item(new String(), new String(), 0, null);
            item.create();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Item item = new Item(null, null, 0, null);
            item.create();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testDomainCreateItemWithInvalidProduct() throws Exception {
        final int id = getUniqueId();

        // Creates an object with a null linked object
        try {
            final Item item = new Item("item" + id, "name" + id, id, null);
            item.create();
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            final Item item = new Item("item" + id, "name" + id, id, new Product());
            item.create();
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testDomainUpdateUnknownItem() throws Exception {
        // Updates an unknown object
        try {
            new Item().update();
            fail("Updating a none existing object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateItemWithInvalidValues() throws Exception {

        // Creates an object
        final int id = getUniqueId();
        createItem(id);

        // Ensures that the object exists
        Item item = null;
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            item.setName(new String());
            item.setUnitCost(0);
            item.update();
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            item.setName(null);
            item.setUnitCost(0);
            item.update();
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail();
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateItem() throws Exception {
        final int id = getUniqueId();

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        Item item = null;
        try {
            item = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findItemSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkItem(item, id);

        // Updates the object with new values
        updateItem(item, id + 1);

        // Ensures that the object still exists
        Item itemUpdated = null;
        try {
            itemUpdated = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkItem(itemUpdated, id + 1);

        // Cleans the test environment
        removeItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findItemSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDomainDeleteUnknownItem() throws Exception {
        // Removes an unknown object
        try {
            new Item().remove();
            fail("Deleting an unknown object should break");
        } catch (CheckException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Item findItem(final int id) throws FinderException, CheckException {
        final Item item = new Item();
        item.findByPrimaryKey("item" + id);
        return item;
    }

    private void findItemSql(final int id) throws ObjectNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final String sql;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            sql = "SELECT * FROM T_ITEM WHERE ID = 'item" + id + "' ";
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

    private int findAllItems() throws FinderException {
        try {
            return new Item().findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int findAllItems(String productId) throws FinderException {
        try {
            return new Item().findAll(productId).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int searchItems(int keyword) throws FinderException {
        try {
            return new Item().search(String.valueOf(keyword)).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first, then a product and then an item linked to this product
    private void createItem(final int id) throws CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        category.create();
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        product.create();
        // Create Item
        final Item item = new Item("item" + id, "name" + id, id, product);
        item.setImagePath("imagePath" + id);
        item.create();
    }

    // Creates a category, a product and updates the item with this new product
    private void updateItem(final Item item, final int id) throws UpdateException, CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        category.create();
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        product.create();
        // Updates the item
        item.setName("name" + id);
        item.setUnitCost(id);
        item.setImagePath("imagePath" + id);
        item.setProduct(product);
        item.update();
    }

    private void checkItem(final Item item, final int id) {
        assertEquals("name", "name" + id, item.getName());
        assertEquals("unitCost", new Double(id), new Double(item.getUnitCost()));
        assertEquals("imagePath", "imagePath" + id, item.getImagePath());
        assertNotNull("product", item.getProduct());
    }

    private void removeItem(final int id) throws RemoveException, CheckException {
        final Item item = new Item("item" + id);
        item.remove();
        final Product product = new Product("prod" + id);
        product.remove();
        final Category category = new Category("cat" + id);
        category.remove();
    }
}
