package com.yaps.petstore.server.service.catalog;

import com.yaps.petstore.common.dto.CategoryDTO;
import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.dto.ProductDTO;
import com.yaps.petstore.common.exception.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * This interface gives a remote view of the CatalogService. Any distant client that wants to call
 * a method on the CatalogService has to use this interface. Because it extends the Remote interface,
 * every method must throw RemoteException.
 */
public interface CatalogServiceRemote extends Remote {

    // ======================================
    // =      Category Business methods     =
    // ======================================

    /**
     * Given a CategoryDTO object, this method creates a Category. It first transforms
     * a CategoryDTO into a Category domain object, uses the Category object to apply the
     * business rules for creation. It then transforms back the Category object into a
     * CategoryDTO.
     *
     * @param categoryDTO cannot be null.
     * @return the created CategoryDTO
     * @throws DuplicateKeyException is thrown if an object with the same id
     *                               already exists in the system
     * @throws CreateException       is thrown if a DomainException is caught
     *                               or a system failure is occurs
     * @throws CheckException        is thrown if a invalid data is found
     * @throws RemoteException       is thrown if a remote call fails
     */
    CategoryDTO createCategory(CategoryDTO categoryDTO) throws CreateException, CheckException, RemoteException;

    /**
     * Given an id this method uses the Category domain object to load all the data of this
     * object. It then transforms this object into a CategoryDTO and retrun it.
     *
     * @param categoryId identifier
     * @return CategoryDTO
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     * @throws RemoteException         is thrown if a remote call fails
     */
    CategoryDTO findCategory(String categoryId) throws FinderException, CheckException, RemoteException;

    /**
     * Given an id, this method finds a Category domain object and then calls its deletion
     * method.
     *
     * @param categoryId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void deleteCategory(String categoryId) throws RemoveException, CheckException, RemoteException;

    /**
     * Given a CategoryDTO object, this method updates a Category. It first transforms
     * a CategoryDTO into a Category domain object and uses the Category object to apply the
     * business rules for modification.
     *
     * @param categoryDTO cannot be null.
     * @throws UpdateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void updateCategory(CategoryDTO categoryDTO) throws UpdateException, CheckException, RemoteException;

    /**
     * This method return all the categories from the system. It uses the Category domain object
     * to get the data. It then transforms this collection of Category object into a
     * collection of CategoryDTO and returns it.
     *
     * @return a collection of CategoryDTO
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws RemoteException         is thrown if a remote call fails
     */
    Collection findCategories() throws FinderException, RemoteException;

    // ======================================
    // =      Product Business methods     =
    // ======================================

    /**
     * Given a ProductDTO object, this method creates a Product. It first transforms
     * a ProductDTO into a Product domain object, uses the Product object to apply the
     * business rules for creation. It then transforms back the Product object into a
     * ProductDTO.
     *
     * @param productDTO cannot be null.
     * @return the created ProductDTO
     * @throws DuplicateKeyException is thrown if an object with the same id
     *                               already exists in the system
     * @throws CreateException       is thrown if a DomainException is caught
     *                               or a system failure is occurs
     * @throws CheckException        is thrown if a invalid data is found
     * @throws RemoteException       is thrown if a remote call fails
     */
    ProductDTO createProduct(ProductDTO productDTO) throws CreateException, CheckException, RemoteException;

    /**
     * Given an id this method uses the Product domain object to load all the data of this
     * object. It then transforms this object into a ProductDTO and returns it.
     *
     * @param productId identifier
     * @return ProductDTO
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     * @throws RemoteException         is thrown if a remote call fails
     */
    ProductDTO findProduct(String productId) throws FinderException, CheckException, RemoteException;

    /**
     * Given an id, this method finds a Product domain object and then calls its deletion
     * method.
     *
     * @param productId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void deleteProduct(String productId) throws RemoveException, CheckException, RemoteException;

    /**
     * Given a ProductDTO object, this method updates a Product. It first transforms
     * a ProductDTO into a Product domain object and uses the Product object to apply the
     * business rules for modification.
     *
     * @param productDTO cannot be null.
     * @throws UpdateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void updateProduct(ProductDTO productDTO) throws UpdateException, CheckException, RemoteException;

    /**
     * This method return all the products from the system. It uses the Product domain object
     * to get the data. It then transforms this collection of Product object into a
     * collection of ProductDTO and returns it.
     *
     * @return a collection of ProductDTO
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws RemoteException         is thrown if a remote call fails
     */
    Collection findProducts() throws FinderException, RemoteException;

    /**
     * This method return all the products for a given category. It uses the Product domain object
     * to get the data. It then transforms this collection of Product object into a
     * collection of ProductDTO and returns it.
     *
     * @param categoryId category identifier cannot be null.
     * @return a collection of ProductDTO
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws RemoteException         is thrown if a remote call fails
     */
    Collection findProducts(String categoryId) throws FinderException, RemoteException;

    // ======================================
    // =        Item Business methods       =
    // ======================================

    /**
     * Given a ItemDTO object, this method creates a Item. It first transforms
     * a ItemDTO into a Item domain object, uses the Item object to apply the
     * business rules for creation. It then transforms back the Item object into a
     * ItemDTO.
     *
     * @param itemDTO cannot be null.
     * @return the created ItemDTO
     * @throws DuplicateKeyException is thrown if an object with the same id
     *                               already exists in the system
     * @throws CreateException       is thrown if a DomainException is caught
     *                               or a system failure is occurs
     * @throws CheckException        is thrown if a invalid data is found
     * @throws RemoteException       is thrown if a remote call fails
     */
    ItemDTO createItem(ItemDTO itemDTO) throws CreateException, CheckException, RemoteException;

    /**
     * Given an id this method uses the Item domain object to load all the data of this
     * object. It then transforms this object into a ItemDTO and returns it.
     *
     * @param itemId identifier
     * @return ItemDTO
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     * @throws RemoteException         is thrown if a remote call fails
     */
    ItemDTO findItem(String itemId) throws FinderException, CheckException, RemoteException;

    /**
     * Given an id, this method finds a Item domain object and then calls its deletion
     * method.
     *
     * @param itemId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void deleteItem(String itemId) throws RemoveException, CheckException, RemoteException;

    /**
     * Given a ItemDTO object, this method updates an Item. It first transforms
     * a ItemDTO into an Item domain object and uses the Item object to apply the
     * business rules for modification.
     *
     * @param itemDTO cannot be null.
     * @throws UpdateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void updateItem(ItemDTO itemDTO) throws UpdateException, CheckException, RemoteException;

    /**
     * This method return all the items from the system. It uses the Item domain object
     * to get the data. It then transforms this collection of Item object into a
     * collection of ItemDTO and returns it.
     *
     * @return a collection of ItemDTO
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws RemoteException         is thrown if a remote call fails
     */
    Collection findItems() throws FinderException, RemoteException;

    /**
     * This method return all the items for a given product. It uses the Item domain object
     * to get the data. It then transforms this collection of Item object into a
     * collection of ItemDTO and returns it.
     *
     * @param productId category identifier cannot be null.
     * @return a collection of ItemDTO
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws RemoteException         is thrown if a remote call fails
     */
    Collection findItems(String productId) throws FinderException, RemoteException;

    /**
     * This method return all the items that match a given keyword. It uses the Item domain object
     * to get the data. It then transforms this collection of Item object into a
     * collection of ItemDTO and returns it.
     *
     * @param keyword
     * @return a collection of ItemDTO
     * @throws ObjectNotFoundException is thrown if the collection is empty
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws RemoteException         is thrown if a remote call fails
     */
    Collection searchItems(String keyword) throws FinderException, RemoteException;
}
