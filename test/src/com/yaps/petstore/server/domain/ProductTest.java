package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.product.Product;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the Product class
 */
public final class ProductTest extends AbstractTestCase {

    public ProductTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(ProductTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to findan object with a invalid identifier.
     */
    public void testDomainFindProductWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = getUniqueId();
        try {
            findProduct(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            new Product().findByPrimaryKey(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            new Product().findByPrimaryKey(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllProducts() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = findAllProducts();

        // Create an object
        createProduct(id);

        // Ensures that the object exists
        try {
            findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllProducts();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllProductsForACategory() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = findAllProducts("cat" + id);

        // Checks that the collection is empty
        if (firstSize != 0) fail("The collection should be empty");

        // Create an object
        createProduct(id);

        // Ensures that the object exists
        try {
            findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // second findAll
        final int secondSize = findAllProducts("cat" + id);

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Create an new object with a different id
        createProduct(id + 1);

        // Ensures that the new object exists
        try {
            findProduct(id + 1);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // third findAll
        final int thirdSize = findAllProducts("cat" + id);

        // Checks that the collection size is the same
        // Because the object created had a different id, it should not be in the collection
        if (thirdSize != secondSize) fail("The collection should have the same size");

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateProduct() throws Exception {
        final int id = getUniqueId();
        Product product = null;

        // Ensures that the object doesn't exist
        try {
            product = findProduct(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findProductSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkProduct(product, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createProduct(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findProductSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateProductWithInvalidValues() throws Exception {

        // Creates an object with an empty values
        try {
            final Product product = new Product(new String(), new String(), new String(), null);
            product.create();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an null values
        try {
            final Product product = new Product(null, null, null, null);
            product.create();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testDomainCreateProductWithInvalidCategory() throws Exception {
        final int id = getUniqueId();

        // Creates an object with no object linked
        try {
            final Product product = new Product("prod" + id, "name" + id, "description" + id, null);
            product.create();
            fail("Object with no object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with a null linked object
        try {
            final Product product = new Product("prod" + id, "name" + id, "description" + id, null);
            product.create();
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            final Product product = new Product("prod" + id, "name" + id, "description" + id, new Category());
            product.create();
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testDomainUpdateUnknownProduct() throws Exception {
        // Updates an unknown object
        try {
            new Product().update();
            fail("Updating a none existing object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateProductWithInvalidValues() throws Exception {

        // Creates an object
        final int id = getUniqueId();
        createProduct(id);

        // Ensures that the object exists
        Product product = null;
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            product.setName(new String());
            product.setDescription(new String());
            product.update();
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            product.setName(null);
            product.setDescription(null);
            product.update();
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail();
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateProduct() throws Exception {
        final int id = getUniqueId();

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        Product product = null;
        try {
            product = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findProductSql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkProduct(product, id);

        // Updates the object with new values
        updateProduct(product, id + 1);

        // Ensures that the object still exists
        Product productUpdated = null;
        try {
            productUpdated = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkProduct(productUpdated, id + 1);

        // Cleans the test environment
        removeProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findProductSql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDomainDeleteUnknownProduct() throws Exception {
        // Removes an unknown object
        try {
            new Product().remove();
            fail("Deleting an unknown object should break");
        } catch (CheckException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Product findProduct(final int id) throws FinderException, CheckException {
        final Product product = new Product();
        product.findByPrimaryKey("prod" + id);
        return product;
    }

    private void findProductSql(final int id) throws ObjectNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final String sql;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            sql = "SELECT * FROM T_PRODUCT WHERE ID = 'prod" + id + "' ";
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

    private int findAllProducts() throws FinderException {
        try {
            return new Product().findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int findAllProducts(String categoryId) throws FinderException {
        try {
            return new Product().findAll(categoryId).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first and then a product linked to this category
    private void createProduct(final int id) throws CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        category.create();
        // Create Product
        final Product product = new Product("prod" + id, "name" + id, "description" + id, category);
        product.create();
    }

    // Creates a category and updates the product with this new category
    private void updateProduct(final Product product, final int id) throws UpdateException, CreateException, CheckException {
        // Create Category
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        category.create();
        // Update Product with new category
        product.setName("name" + id);
        product.setDescription("description" + id);
        product.setCategory(category);
        product.update();
    }

    private void removeProduct(final int id) throws RemoveException, CheckException {
        final Product product = new Product("prod" + id);
        product.remove();
        final Category category = new Category("cat" + id);
        category.remove();
    }

    private void checkProduct(final Product product, final int id) {
        assertEquals("name", "name" + id, product.getName());
        assertEquals("description", "description" + id, product.getDescription());
        assertNotNull("category", product.getCategory());
    }
}
