package com.yaps.petstore.server.domain;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.server.domain.category.Category;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the Category class
 */
public final class CategoryTest extends AbstractTestCase {

    public CategoryTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CategoryTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testDomainFindCategoryWithInvalidValues() throws Exception {

        // Finds an object with a unknown identifier
        final int id = getUniqueId();
        try {
            findCategory(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            new Category().findByPrimaryKey(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            new Category().findByPrimaryKey(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testDomainFindAllCategories() throws Exception {
        final int id = getUniqueId();

        // First findAll
        final int firstSize = findAllCategories();

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        try {
            findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllCategories();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        removeCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it and checks it then exists.
     */
    public void testDomainCreateCategory() throws Exception {
        final int id = getUniqueId();
        Category category = null;

        // Ensures that the object doesn't exist
        try {
            category = findCategory(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findCategorySql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkCategory(category, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCategory(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        removeCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findCategorySql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testDomainCreateCategoryWithInvalidValues() throws Exception {

        // Creates an object with empty values
        try {
            final Category category = new Category(new String(), new String(), new String());
            category.create();
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            final Category category = new Category(null, null, null);
            category.create();
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an unknown object.
     */
    public void testDomainUpdateUnknownCategory() throws Exception {
        // Updates an unknown object
        try {
            new Category().update();
            fail("Updating a none existing object should break");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testDomainUpdateCategoryWithInvalidValues() throws Exception {

        // Creates an object
        final int id = getUniqueId();
        createCategory(id);

        // Ensures that the object exists
        Category category = null;
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Updates the object with empty values
        try {
            category.setName(new String());
            category.setDescription(new String());
            category.update();
            fail("Updating an object with empty values should break");
        } catch (CheckException e) {
        }

        // Updates the object with null values
        try {
            category.setName(null);
            category.setDescription(null);
            category.update();
            fail("Updating an object with null values should break");
        } catch (CheckException e) {
        }

        // Ensures that the object still exists
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Cleans the test environment
        removeCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testDomainUpdateCategory() throws Exception {
        final int id = getUniqueId();

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        Category category = null;
        try {
            category = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Ensures that the object exists in the database by executing a sql statement
        try {
            findCategorySql(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found in the database");
        }

        // Checks that it's the right object
        checkCategory(category, id);

        // Updates the object with new values
        updateCategory(category, id + 1);

        // Ensures that the object still exists
        Category categoryUpdated = null;
        try {
            categoryUpdated = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCategory(categoryUpdated, id + 1);

        // Cleans the test environment
        removeCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        try {
            findCategorySql(id);
            fail("Object has been deleted it shouldn't be found in the database");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testDomainDeleteUnknownCategory() throws Exception {
        // Removes an unknown object
        try {
            new Category().remove();
            fail("Deleting an unknown object should break");
        } catch (CheckException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Category findCategory(final int id) throws FinderException, CheckException {
        final Category category = new Category();
        category.findByPrimaryKey("cat" + id);
        return category;
    }

    private void findCategorySql(final int id) throws ObjectNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        final String sql;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            // Select a Row
            sql = "SELECT * FROM T_CATEGORY WHERE ID = 'cat" + id + "' ";
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

    private int findAllCategories() throws FinderException {
        try {
            return new Category().findAll().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private void createCategory(final int id) throws CreateException, CheckException {
        final Category category = new Category("cat" + id, "name" + id, "description" + id);
        category.create();
    }

    private void updateCategory(final Category category, final int id) throws UpdateException, CheckException {
        category.setName("name" + id);
        category.setDescription("description" + id);
        category.update();
    }

    private void removeCategory(final int id) throws CheckException, RemoveException {
        final Category category = new Category("cat" + id);
        category.remove();
    }

    private void checkCategory(final Category category, final int id) {
        assertEquals("name", "name" + id, category.getName());
        assertEquals("description", "description" + id, category.getDescription());
    }
}
