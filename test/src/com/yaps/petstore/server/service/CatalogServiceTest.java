package com.yaps.petstore.server.service;

import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.dto.CategoryDTO;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.dto.ProductDTO;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.server.service.catalog.CatalogService;
import junit.framework.TestSuite;

import java.rmi.RemoteException;

/**
 * This class tests the CatalogService class
 */
public final class CatalogServiceTest extends AbstractTestCase {

    public CatalogServiceTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CatalogServiceTest.class);
    }

    //==================================
    //=   Test cases for Category      =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindCategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds an object with a unknown identifier
        final String id = getUniqueStringId();
        try {
            service.findCategory(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findCategory(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findCategory(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllCategories() throws Exception {
        final String id = getUniqueStringId();

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
        deleteCategory(id);

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
    public void testServiceCreateCategory() throws Exception {
        final String id = getUniqueStringId();
        CategoryDTO categoryDTO = null;

        // Ensures that the object doesn't exist
        try {
            findCategory(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        try {
            categoryDTO = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCategory(categoryDTO, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCategory(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateCategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        CategoryDTO categoryDTO;

        // Creates an object with a null parameter
        try {
            service.createCategory(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            categoryDTO = new CategoryDTO(new String(), new String(), new String());
            service.createCategory(categoryDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            categoryDTO = new CategoryDTO(null, null, null);
            service.createCategory(categoryDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testServiceUpdateCategory() throws Exception {
        final String id = getUniqueStringId();
        final String updatedId = getUniqueStringId();

        // Creates an object
        createCategory(id);

        // Ensures that the object exists
        CategoryDTO categoryDTO = null;
        try {
            categoryDTO = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCategory(categoryDTO, id);

        // Updates the object with new values
        updateCategory(categoryDTO, updatedId);

        // Ensures that the object still exists
        CategoryDTO categoryUpdated = null;
        try {
            categoryUpdated = findCategory(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkCategory(categoryUpdated, updatedId);

        // Cleans the test environment
        deleteCategory(id);

        try {
            findCategory(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testServiceUpdateCategoryWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        CategoryDTO categoryDTO;

        // Updates an object with a null parameter
        try {
            service.updateCategory(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
            categoryDTO = new CategoryDTO(new String(), new String(), new String());
            service.updateCategory(categoryDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            categoryDTO = new CategoryDTO(null, null, null);
            service.updateCategory(categoryDTO);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteUnknownCategory() throws Exception {
        final String id = getUniqueStringId();

        // Ensures that the object doesn't exist
        try {
            findCategory(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteCategory(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }


    //==================================
    //=    Test cases for product      =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds an object with a unknown identifier
        final String id = getUniqueStringId();
        try {
            service.findProduct(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findProduct(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findProduct(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllProducts() throws Exception {
        final String id = getUniqueStringId();

        // First findAll
        final int firstSize = findAllProducts();

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllProducts();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteProduct(id);

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
    public void testServiceFindAllProductsForACategory() throws Exception {
        final String id = getUniqueStringId();

        // First findAll
        final int firstSize = findAllProducts("cat" + id);

        // Checks that the collection is empty
        if (firstSize != 0) fail("The collection should be empty");

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
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
        deleteProduct(id);

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
    public void testServiceCreateProduct() throws Exception {
        final String id = getUniqueStringId();
        ProductDTO productDTO = null;

        // Ensures that the object doesn't exist
        try {
            findProduct(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        try {
            productDTO = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProduct(productDTO, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createProduct(id);
            fail("An object with the same id has already been created");
        } catch (DuplicateKeyException e) {
        }

        // Cleans the test environment
        deleteProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        ProductDTO productDTO;

        // Creates an object with a null parameter
        try {
            service.createProduct(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            productDTO = new ProductDTO(new String(), new String(), new String());
            service.createProduct(productDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            productDTO = new ProductDTO(null, null, null);
            service.createProduct(productDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testServiceCreateProductWithInvalidCategory() throws Exception {
        final int id = getUniqueId();
        final CatalogService service = getCatalogService();
        ProductDTO product;

        // Creates an object with no object linked
        try {
            product = new ProductDTO("prod" + id, "name" + id, "description" + id);
            service.createProduct(product);
            fail("Object with no object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with a null linked object
        try {
            product = new ProductDTO("prod" + id, "name" + id, "description" + id);
            product.setCategoryId(null);
            service.createProduct(product);
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            product = new ProductDTO("prod" + id, "name" + id, "description" + id);
            product.setCategoryId(new String());
            service.createProduct(product);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an unknown linked object
        try {
            product = new ProductDTO("prod" + id, "name" + id, "description" + id);
            product.setCategoryId("cat" + id);
            service.createProduct(product);
            fail("Object with an unknown object linked should not be created");
        } catch (CreateException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testServiceUpdateProduct() throws Exception {
        final String id = getUniqueStringId();
        final String updatedId = getUniqueStringId();

        // Creates an object
        createProduct(id);

        // Ensures that the object exists
        ProductDTO productDTO = null;
        try {
            productDTO = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkProduct(productDTO, id);

        // Updates the object with new values
        updateProduct(productDTO, updatedId);

        // Ensures that the object still exists
        ProductDTO productUpdated = null;
        try {
            productUpdated = findProduct(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkProduct(productUpdated, updatedId);

        // Cleans the test environment
        deleteProduct(id);

        try {
            findProduct(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testServiceUpdateProductWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        ProductDTO productDTO;

        // Updates an object with a null parameter
        try {
            service.updateProduct(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
            productDTO = new ProductDTO(new String(), new String(), new String());
            service.updateProduct(productDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            productDTO = new ProductDTO(null, null, null);
            service.updateProduct(productDTO);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteUnknownProduct() throws Exception {
        final String id = getUniqueStringId();

        // Ensures that the object doesn't exist
        try {
            findProduct(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteProduct(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }

    //==================================
    //=      Test cases for item       =
    //==================================
    /**
     * This test tries to find an object with a invalid identifier.
     */
    public void testServiceFindItemWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();

        // Finds an object with a unknown identifier
        final String id = getUniqueStringId();
        try {
            service.findItem(id);
            fail("Object with unknonw id should not be found");
        } catch (ObjectNotFoundException e) {
        }

        // Finds an object with an empty identifier
        try {
            service.findItem(new String());
            fail("Object with empty id should not be found");
        } catch (CheckException e) {
        }

        // Finds an object with a null identifier
        try {
            service.findItem(null);
            fail("Object with null id should not be found");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the method findAll works. It does a first findAll, creates
     * a new object and does a second findAll.
     */
    public void testServiceFindAllItems() throws Exception {
        final String id = getUniqueStringId();

        // First findAll
        final int firstSize = findAllItems();

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
        final int secondSize = findAllItems();

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Cleans the test environment
        deleteItem(id);

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
    public void testServiceFindAllItemsForAProduct() throws Exception {
        final String id = getUniqueStringId();

        // First findAll
        final int firstSize = findAllItems("prod" + id);

        // Checks that the collection is empty
        if (firstSize != 0) fail("The collection should be empty");

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second findAll
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
        deleteItem(id);

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
    public void testServiceSearchItems() throws Exception {
        final String id = getUniqueStringId();

        // First search
        final int firstSize = searchItems(id);

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Second search
        final int secondSize = searchItems(id);

        // Checks that the collection size has increase of one
        if (firstSize + 1 != secondSize) fail("The collection size should have increased by 1");

        // Create an new object with a different id
        createItem(String.valueOf(Integer.parseInt(id) + 1));

        // Ensures that the new object exists
        try {
            findItem(String.valueOf(Integer.parseInt(id) + 1));
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // third search
        final int thirdSize = searchItems(id);

        // Checks that the collection size is the same
        // Because the object created had a different id, it should not be in the collection
        if (thirdSize != secondSize) fail("The collection should have the same size");

        // Cleans the test environment
        deleteItem(id);

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
    public void testServiceCreateItem() throws Exception {
        final String id = getUniqueStringId();
        ItemDTO itemDTO = null;

        // Ensures that the object doesn't exist
        try {
            findItem(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        try {
            itemDTO = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(itemDTO, id);

        // Cleans the test environment
        deleteItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServiceCreateItemWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        ItemDTO itemDTO;

        // Creates an object with a null parameter
        try {
            service.createItem(null);
            fail("Object with null parameter should not be created");
        } catch (CreateException e) {
        }

        // Creates an object with empty values
        try {
            itemDTO = new ItemDTO(new String(), new String(), 0);
            service.createItem(itemDTO);
            fail("Object with empty values should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with null values
        try {
            itemDTO = new ItemDTO(null, null, 0);
            service.createItem(itemDTO);
            fail("Object with null values should not be created");
        } catch (CheckException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid linked object.
     */
    public void testServiceCreateItemWithInvalidProduct() throws Exception {
        final CatalogService service = getCatalogService();
        final int id = getUniqueId();
        ItemDTO item;

        // Creates an object with no object linked
        try {
            item = new ItemDTO("item" + id, "name" + id, id);
            service.createItem(item);
            fail("Object with no object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with a null linked object
        try {
            item = new ItemDTO("item" + id, "name" + id, id);
            item.setProductId(null);
            service.createItem(item);
            fail("Object with null object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an empty linked object
        try {
            item = new ItemDTO("item" + id, "name" + id, id);
            item.setProductId(new String());
            service.createItem(item);
            fail("Object with an empty object linked should not be created");
        } catch (CheckException e) {
        }

        // Creates an object with an unknown linked object
        try {
            item = new ItemDTO("item" + id, "name" + id, id);
            item.setProductId("prod" + id);
            service.createItem(item);
            fail("Object with an unknown object linked should not be created");
        } catch (CreateException e) {
        }
    }

    /**
     * This test make sure that updating an object success
     */
    public void testServiceUpdateItem() throws Exception {
        final String id = getUniqueStringId();
        final String updatedId = getUniqueStringId();

        // Creates an object
        createItem(id);

        // Ensures that the object exists
        ItemDTO itemDTO = null;
        try {
            itemDTO = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkItem(itemDTO, id);

        // Updates the object with new values
        updateItem(itemDTO, updatedId);

        // Ensures that the object still exists
        ItemDTO itemUpdated = null;
        try {
            itemUpdated = findItem(id);
        } catch (ObjectNotFoundException e) {
            fail("Object should be found");
        }

        // Checks that the object values have been updated
        checkItem(itemUpdated, updatedId);

        // Cleans the test environment
        deleteItem(id);

        try {
            findItem(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to update an object with a invalid values.
     */
    public void testServiceUpdateItemWithInvalidValues() throws Exception {
        final CatalogService service = getCatalogService();
        ItemDTO itemDTO;

        // Updates an object with a null parameter
        try {
            service.updateItem(null);
            fail("Object with null parameter should not be updated");
        } catch (UpdateException e) {
        }

        // Updates an object with empty values
        try {
            itemDTO = new ItemDTO(new String(), new String(), 0);
            service.updateItem(itemDTO);
            fail("Object with empty values should not be updated");
        } catch (CheckException e) {
        }

        // Updates an object with null values
        try {
            itemDTO = new ItemDTO(null, null, 0);
            service.updateItem(itemDTO);
            fail("Object with null values should not be updated");
        } catch (CheckException e) {
        }
    }

    /**
     * This test ensures that the system cannont remove an unknown object
     */
    public void testServiceDeleteUnknownItem() throws Exception {
        final String id = getUniqueStringId();

        // Ensures that the object doesn't exist
        try {
            findItem(id);
            fail("Object has not been created it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Delete the unknown object
        try {
            deleteItem(id);
            fail("Deleting an unknown object should break");
        } catch (RemoveException e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private CatalogService getCatalogService() throws RemoteException {
        return new CatalogService();
    }

    //==================================
    //=  Private Methods for Category  =
    //==================================
    private CategoryDTO findCategory(final String id) throws FinderException, CheckException, RemoteException {
        final CategoryDTO categoryDTO = getCatalogService().findCategory("cat" + id);
        return categoryDTO;
    }

    private int findAllCategories() throws FinderException, RemoteException {
        try {
            return getCatalogService().findCategories().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private void createCategory(final String id) throws CreateException, CheckException, RemoteException {
        final CategoryDTO categoryDTO = new CategoryDTO("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(categoryDTO);
    }

    private void updateCategory(final CategoryDTO categoryDTO, final String id) throws UpdateException, CheckException, RemoteException {
        categoryDTO.setName("name" + id);
        categoryDTO.setDescription("description" + id);
        getCatalogService().updateCategory(categoryDTO);
    }

    private void deleteCategory(final String id) throws RemoveException, CheckException, RemoteException {
        getCatalogService().deleteCategory("cat" + id);
    }

    private void checkCategory(final CategoryDTO categoryDTO, final String id) {
        assertEquals("name", "name" + id, categoryDTO.getName());
        assertEquals("description", "description" + id, categoryDTO.getDescription());
    }

    //==================================
    //=  Private Methods for Product   =
    //==================================
    private ProductDTO findProduct(final String id) throws FinderException, CheckException, RemoteException {
        final ProductDTO productDTO = getCatalogService().findProduct("prod" + id);
        return productDTO;
    }

    private int findAllProducts() throws FinderException, RemoteException {
        try {
            return getCatalogService().findProducts().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int findAllProducts(String categoryId) throws FinderException, RemoteException {
        final CatalogService service = getCatalogService();
        try {
            return service.findProducts(categoryId).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first and then a product linked to this category
    private void createProduct(final String id) throws CreateException, CheckException, RemoteException {
        // Create Category
        final CategoryDTO categoryDTO = new CategoryDTO("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
        final ProductDTO productDTO = new ProductDTO("prod" + id, "name" + id, "description" + id);
        productDTO.setCategoryId("cat" + id);
        getCatalogService().createProduct(productDTO);
    }

    // Creates a category and updates the product with this new category
    private void updateProduct(final ProductDTO productDTO, final String id) throws UpdateException, CheckException, RemoteException, CreateException {
        // Create Category
        final CategoryDTO categoryDTO = new CategoryDTO("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(categoryDTO);
        // Update Product with new category
        productDTO.setName("name" + id);
        productDTO.setDescription("description" + id);
        productDTO.setCategoryId("cat" + id);
        getCatalogService().updateProduct(productDTO);
    }

    private void deleteProduct(final String id) throws RemoveException, CheckException, RemoteException {
        getCatalogService().deleteProduct("prod" + id);
        getCatalogService().deleteCategory("cat" + id);
    }

    private void checkProduct(final ProductDTO productDTO, final String id) {
        assertEquals("name", "name" + id, productDTO.getName());
        assertEquals("description", "description" + id, productDTO.getDescription());
    }

    //==================================
    //=    Private Methods for Item    =
    //==================================
    private ItemDTO findItem(final String id) throws CheckException, FinderException, RemoteException {
        final ItemDTO itemDTO = getCatalogService().findItem("item" + id);
        return itemDTO;
    }

    private int findAllItems() throws FinderException, RemoteException {
        try {
            return getCatalogService().findItems().size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int findAllItems(String productId) throws FinderException, RemoteException {
        final CatalogService service = getCatalogService();
        try {
            return service.findItems(productId).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    private int searchItems(String keyword) throws FinderException, RemoteException {
        final CatalogService service = getCatalogService();
        try {
            return service.searchItems(keyword).size();
        } catch (ObjectNotFoundException e) {
            return 0;
        }
    }

    // Creates a category first, then a product and then an item linked to this product
    private void createItem(final String id) throws CreateException, CheckException, RemoteException {
        // Create Category
        final CategoryDTO categoryDTO = new CategoryDTO("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
        final ProductDTO productDTO = new ProductDTO("prod" + id, "name" + id, "description" + id);
        productDTO.setCategoryId("cat" + id);
        getCatalogService().createProduct(productDTO);
        // Create Item
        final ItemDTO itemDTO = new ItemDTO("item" + id, "name" + id, Double.parseDouble(id));
        itemDTO.setImagePath("imagePath" + id);
        itemDTO.setProductId("prod" + id);
        getCatalogService().createItem(itemDTO);
    }

    // Creates a category, a product and updates the item with this new product
    private void updateItem(final ItemDTO itemDTO, final String id) throws UpdateException, CheckException, RemoteException, CreateException {
        // Create Category
        final CategoryDTO categoryDTO = new CategoryDTO("cat" + id, "name" + id, "description" + id);
        getCatalogService().createCategory(categoryDTO);
        // Create Product
        final ProductDTO productDTO = new ProductDTO("prod" + id, "name" + id, "description" + id);
        productDTO.setCategoryId("cat" + id);
        getCatalogService().createProduct(productDTO);
        // Updates the item
        itemDTO.setName("name" + id);
        itemDTO.setUnitCost(Double.parseDouble(id));
        itemDTO.setImagePath("imagePath" + id);
        itemDTO.setProductId("prod" + id);
        getCatalogService().updateItem(itemDTO);
    }

    private void deleteItem(final String id) throws RemoveException, CheckException, RemoteException {
        getCatalogService().deleteItem("item" + id);
        getCatalogService().deleteProduct("prod" + id);
        getCatalogService().deleteCategory("cat" + id);
    }

    private void checkItem(final ItemDTO itemDTO, final String id) {
        assertEquals("name", "name" + id, itemDTO.getName());
        assertEquals("unitCost", new Double(id), new Double(itemDTO.getUnitCost()));
        assertEquals("imagePath", "imagePath" + id, itemDTO.getImagePath());
    }
}

