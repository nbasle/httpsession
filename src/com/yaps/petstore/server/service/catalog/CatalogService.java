package com.yaps.petstore.server.service.catalog;

import com.yaps.petstore.common.dto.CategoryDTO;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.dto.ProductDTO;
import com.yaps.petstore.common.exception.*;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.domain.item.Item;
import com.yaps.petstore.server.domain.product.Product;
import com.yaps.petstore.server.service.AbstractRemoteService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class is a facade for all catalog services.
 */
public final class CatalogService extends AbstractRemoteService implements CatalogServiceRemote {

    // ======================================
    // =            Constructors            =
    // ======================================
    public CatalogService() throws RemoteException {
    }

    // ======================================
    // =      Category Business methods     =
    // ======================================
    public CategoryDTO createCategory(final CategoryDTO categoryDTO) throws CreateException, CheckException {
        final String mname = "createCategory";
        Trace.entering(getCname(), mname, categoryDTO);

        if (categoryDTO == null)
            throw new CreateException("Category object is null");

        // Transforms DTO into domain object
        final Category category = new Category(categoryDTO.getId(), categoryDTO.getName(), categoryDTO.getDescription());

        // Creates the object
        category.create();

        // Transforms domain object into DTO
        final CategoryDTO result = transformCategory2DTO(category);

        Trace.exiting(getCname(), mname, result);
        return result;
    }

    public CategoryDTO findCategory(final String categoryId) throws FinderException, CheckException {
        final String mname = "findCategory";
        Trace.entering(getCname(), mname, categoryId);

        final Category category = new Category();

        // Finds the object
        category.findByPrimaryKey(categoryId);

        // Transforms domain object into DTO
        final CategoryDTO categoryDTO = transformCategory2DTO(category);

        Trace.exiting(getCname(), mname, categoryDTO);
        return categoryDTO;
    }

    public void deleteCategory(final String categoryId) throws RemoveException, CheckException {
        final String mname = "deleteCategory";
        Trace.entering(getCname(), mname, categoryId);

        final Category category = new Category();

            // Checks if the object exists
        try {
            category.findByPrimaryKey(categoryId);
        } catch (FinderException e) {
            throw new RemoveException("Category must exist to be deleted");
        }

        // Deletes the object
        category.remove();
    }

    public void updateCategory(final CategoryDTO categoryDTO) throws UpdateException, CheckException {
        final String mname = "updateCategory";
        Trace.entering(getCname(), mname, categoryDTO);

        if (categoryDTO == null)
            throw new UpdateException("Category object is null");

        Category category = new Category();

            // Checks if the object exists
        try {
            category.findByPrimaryKey(categoryDTO.getId());
        } catch (FinderException e) {
            throw new UpdateException("Category must exist to be updated");
        }

        // Transforms DTO into domain object
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        // Updates the object
        category.update();
    }

    public Collection findCategories() throws FinderException {
        final String mname = "findCategories";
        Trace.entering(getCname(), mname);

        // Finds all the objects
        final Collection categories = new Category().findAll();

        // Transforms domain objects into DTOs
        final Collection categoriesDTO = transformCategories2DTOs(categories);

        Trace.exiting(getCname(), mname, new Integer(categoriesDTO.size()));
        return categoriesDTO;
    }

    // ======================================
    // =      Product Business methods     =
    // ======================================
    public ProductDTO createProduct(final ProductDTO productDTO) throws CreateException, CheckException {
        final String mname = "createProduct";
        Trace.entering(getCname(), mname, productDTO);

        if (productDTO == null)
            throw new CreateException("Product object is null");

        // Finds the linked object
        final Category category = new Category();
        try {
            category.findByPrimaryKey(productDTO.getCategoryId());
        } catch (FinderException e) {
            throw new CreateException("Category must exist to create a product");
        }

            // Transforms DTO into domain object
        final Product product = new Product(productDTO.getId(), productDTO.getName(), productDTO.getDescription(), category);

            // Creates the object
            product.create();

            // Transforms domain object into DTO
        final ProductDTO result = transformProduct2DTO(product);

        Trace.exiting(getCname(), mname, result);
        return result;
    }

    public ProductDTO findProduct(final String productId) throws FinderException, CheckException {
        final String mname = "findProduct";
        Trace.entering(getCname(), mname, productId);

        final Product product = new Product();

        // Finds the object
        product.findByPrimaryKey(productId);

        // Transforms domain object into DTO
        final ProductDTO productDTO = transformProduct2DTO(product);

        Trace.exiting(getCname(), mname, productDTO);
        return productDTO;
    }

    public void deleteProduct(final String productId) throws RemoveException, CheckException {
        final String mname = "deleteProduct";
        Trace.entering(getCname(), mname, productId);

        final Product product = new Product();

            // Checks if the object exists
        try {
            product.findByPrimaryKey(productId);
        } catch (FinderException e) {
            throw new RemoveException("Product must exist to be deleted");
        }

        // Deletes the object
        product.remove();
    }

    public void updateProduct(final ProductDTO productDTO) throws UpdateException, CheckException {
        final String mname = "updateProduct";
        Trace.entering(getCname(), mname, productDTO);

        if (productDTO == null)
            throw new UpdateException("Product object is null");

        Product product = new Product();

            // Checks if the object exists
        try {
            product.findByPrimaryKey(productDTO.getId());
        } catch (FinderException e) {
            throw new UpdateException("Product must exist to be updated");
        }

        // Finds the linked object
        final Category category = new Category();
        try {
            category.findByPrimaryKey(productDTO.getCategoryId());
        } catch (FinderException e) {
            throw new UpdateException("Category must exist to update a product");
        }

        // Transforms DTO into domain object
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);

        // Updates the object
        product.update();
    }

    public Collection findProducts() throws FinderException {
        final String mname = "findProducts";
        Trace.entering(getCname(), mname);

        // Finds all the objects
        final Collection products = new Product().findAll();

            // Transforms domain objects into DTOs
        Collection productsDTO = transformProducts2DTOs(products);

        Trace.exiting(getCname(), mname, new Integer(productsDTO.size()));
        return productsDTO;
    }

    public Collection findProducts(String categoryId) throws FinderException {
        final String mname = "findProducts";
        Trace.entering(getCname(), mname, categoryId);

        // Finds all the products
        final Collection products = new Product().findAll(categoryId);

            // Transforms domain objects into DTOs
        Collection productsDTO = transformProducts2DTOs(products);

        Trace.exiting(getCname(), mname, new Integer(productsDTO.size()));
        return productsDTO;
    }

    // ======================================
    // =        Item Business methods       =
    // ======================================
    public ItemDTO createItem(final ItemDTO itemDTO) throws CreateException, CheckException {
        final String mname = "createItem";
        Trace.entering(getCname(), mname, itemDTO);

        if (itemDTO == null)
            throw new CreateException("Item object is null");

        // Finds the linked object
        final Product product = new Product();
        try {
            product.findByPrimaryKey(itemDTO.getProductId());
        } catch (FinderException e) {
            throw new CreateException("Product must exist to create an item");
        }

            // Transforms DTO into domain object
        final Item item = new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getUnitCost(), product);
        item.setImagePath(itemDTO.getImagePath());

            // Creates the object
            item.create();

            // Transforms domain object into DTO
        final ItemDTO result = transformItem2DTO(item);

        Trace.exiting(getCname(), mname, result);
        return result;
    }

    public ItemDTO findItem(final String itemId) throws FinderException, CheckException {
        final String mname = "findItem";
        Trace.entering(getCname(), mname, itemId);

        final Item item = new Item();

        // Finds the object
        item.findByPrimaryKey(itemId);

        // Transforms domain object into DTO
        final ItemDTO itemDTO = transformItem2DTO(item);

        Trace.exiting(getCname(), mname, itemDTO);
        return itemDTO;
    }

    public void deleteItem(final String itemId) throws RemoveException, CheckException {
        final String mname = "deleteItem";
        Trace.entering(getCname(), mname, itemId);

        final Item item = new Item();

            // Checks if the object exists
        try {
            item.findByPrimaryKey(itemId);
        } catch (FinderException e) {
            throw new RemoveException("Item must exist to be deleted");
        }

        // Deletes the object
        item.remove();
    }

    public void updateItem(final ItemDTO itemDTO) throws UpdateException, CheckException {
        final String mname = "updateItem";
        Trace.entering(getCname(), mname, itemDTO);

        if (itemDTO == null)
            throw new UpdateException("Item object is null");

        Item item = new Item();

            // Checks if the object exists
        try {
            item.findByPrimaryKey(itemDTO.getId());
        } catch (FinderException e) {
            throw new UpdateException("Item must exist to be updated");
        }

        // Finds the linked object
        final Product product = new Product();
        try {
            product.findByPrimaryKey(itemDTO.getProductId());
        } catch (FinderException e) {
            throw new UpdateException("Product must exist to update an item");
        }

        // Transforms DTO into domain object
        item.setName(itemDTO.getName());
        item.setUnitCost(itemDTO.getUnitCost());
        item.setImagePath(itemDTO.getImagePath());
        item.setProduct(product);

        // Updates the object
        item.update();
    }

    public Collection findItems() throws FinderException {
        final String mname = "findItems";
        Trace.entering(getCname(), mname);

        // Finds all the objects
        final Collection items = new Item().findAll();

            // Transforms domain objects into DTOs
        final Collection itemsDTO = transformItems2DTOs(items);

        Trace.exiting(getCname(), mname, new Integer(itemsDTO.size()));
        return itemsDTO;
    }

    public Collection findItems(final String productId) throws FinderException {
        final String mname = "findItems";
        Trace.entering(getCname(), mname, productId);

        // Finds all the items
        final Collection items = new Item().findAll(productId);

            // Transforms domain objects into DTOs
        final Collection itemsDTO = transformItems2DTOs(items);

        Trace.exiting(getCname(), mname, new Integer(itemsDTO.size()));
        return itemsDTO;
    }

    public Collection searchItems(final String keyword) throws FinderException {
        final String mname = "searchItems";
        Trace.entering(getCname(), mname, keyword);

        // Search all the items
        final Collection items = new Item().search(keyword);

            // Transforms domain objects into DTOs
        final Collection itemsDTO = transformItems2DTOs(items);

        Trace.exiting(getCname(), mname, new Integer(itemsDTO.size()));
        return itemsDTO;
    }

    // ======================================
    // =          Private Methods           =
    // ======================================
    // Category
    private CategoryDTO transformCategory2DTO(final Category category) {
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    private Collection transformCategories2DTOs(final Collection categories) {
        final Collection categoriesDTO = new ArrayList();
        for (Iterator iterator = categories.iterator(); iterator.hasNext();) {
            final Category category = (Category) iterator.next();
            categoriesDTO.add(transformCategory2DTO(category));
        }
        return categoriesDTO;
    }

    // Product
    private ProductDTO transformProduct2DTO(final Product product) {
        final ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        // Retreives the data for the linked object
        try {
        product.getCategory().findByPrimaryKey(product.getCategory().getId());
        } catch (FinderException e) {
            // No exception can occur
        } catch (CheckException e) {
            // No exception can occur
        }
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setCategoryName(product.getCategory().getName());
        return productDTO;
    }

    private Collection transformProducts2DTOs(final Collection products) {
        final Collection productsDTO = new ArrayList();
        for (Iterator iterator = products.iterator(); iterator.hasNext();) {
            final Product product = (Product) iterator.next();
            productsDTO.add(transformProduct2DTO(product));
        }
        return productsDTO;
    }

    // Item
    private ItemDTO transformItem2DTO(final Item item) {
        final ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setUnitCost(item.getUnitCost());
        itemDTO.setImagePath(item.getImagePath());
        // Retreives the data for the linked object
        try {
        item.getProduct().findByPrimaryKey(item.getProduct().getId());
        } catch (FinderException e) {
            // No exception can occur
        } catch (CheckException e) {
            // No exception can occur
        }
        itemDTO.setProductId(item.getProduct().getId());
        itemDTO.setProductName(item.getProduct().getName());
        itemDTO.setProductDescription(item.getProduct().getDescription());
        return itemDTO;
    }

    private Collection transformItems2DTOs(final Collection items) {
        final Collection itemsDTO = new ArrayList();
        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
            final Item item = (Item) iterator.next();
            itemsDTO.add(transformItem2DTO(item));
        }
        return itemsDTO;
    }

}
