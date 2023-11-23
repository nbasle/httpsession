package com.yaps.petstore.server.domain.product;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.category.Category;

import java.util.Collection;

/**
 * This class represents a Product in the catalog of the YAPS company.
 * The catalog is divided into catagories. Each one divided into products
 * and each product in items.
 */
public final class Product extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _name;
    private String _description;
    private Category _category;
    private Collection _items;

    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new ProductDAO();
    }

    public Product() {
    }

    public Product(final String id) {
        setId(id);
    }

    public Product(final String id, final String name, final String description, final Category category) {
        setId(id);
        setName(name);
        setDescription(description);
        setCategory(category);
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This methods returns all the productss for a given category.
     *
     * @param categoryId category id from which we want all the products
     * @return a collection of Product
     * @throws com.yaps.petstore.common.exception.ObjectNotFoundException
     *                         thrown if the id is not found
     * @throws FinderException thrown if a system failure occurs
     */
    public Collection findAll(final String categoryId) throws FinderException {
        final String mname = "findAll";
        Trace.entering(getCname(), mname, categoryId);

        // Uses the DAO to access the persistent layer
        final Collection products = getDAO().selectAll(categoryId);

        Trace.exiting(getCname(), mname, new Integer(products.size()));
        return products;
    }

    // ======================================
    // =          Protected methods         =
    // ======================================
    protected void loadObject(final Object object) {
        final Product temp = (Product) object;

        // Sets data to current object
        setId(temp.getId());
        setName(temp.getName());
        setDescription(temp.getDescription());
        setCategory(temp.getCategory());
        setItems(temp.getItems());
    }

    protected void checkData() throws CheckException {
        checkId(getId());
        if (getName() == null || "".equals(getName()))
            throw new CheckException("Invalid name");
        if (getDescription() == null || "".equals(getDescription()))
            throw new CheckException("Invalid description");
        if (getCategory() == null || getCategory().getId() == null || "".equals(getCategory().getId()))
            throw new CheckException("Invalid category");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    private ProductDAO getDAO() {
        return (ProductDAO) _dao;
    }

    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        _name = name;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(final String description) {
        _description = description;
    }

    public Category getCategory() {
        return _category;
    }

    public void setCategory(final Category category) {
        _category = category;
    }

    public Collection getItems() {
        return _items;
    }

    private void setItems(final Collection items) {
        _items = items;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Product{");
        buf.append("id=").append(getId());
        buf.append(",name=").append(getName());
        buf.append(",description=").append(getDescription());
        buf.append('}');
        return buf.toString();
    }
}