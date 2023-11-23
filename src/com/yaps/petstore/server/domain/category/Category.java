package com.yaps.petstore.server.domain.category;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.PersistentObject;

import java.util.Collection;

/**
 * This class represents a Category in the catalog of the YAPS company.
 * The catalog is divided into catagories. Each one divided into products
 * and each product in items.
 */
public final class Category extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _name;
    private String _description;
    private Collection _products;

    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new CategoryDAO();
    }

    public Category() {
    }

    public Category(final String id) {
        setId(id);
    }

    public Category(final String id, final String name, final String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    // ======================================
    // =          Protected methods         =
    // ======================================
    protected void loadObject(final Object object) {
        final Category temp = (Category) object;

        // Sets data to current object
        setId(temp.getId());
        setName(temp.getName());
        setDescription(temp.getDescription());
        setProducts(temp.getProducts());
    }

    protected void checkData() throws CheckException {
        checkId(getId());
        if (getName() == null || "".equals(getName()))
            throw new CheckException("Invalid name");
        if (getDescription() == null || "".equals(getDescription()))
            throw new CheckException("Invalid description");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
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

    public Collection getProducts() {
        return _products;
    }

    private void setProducts(final Collection products) {
        _products = products;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Category{");
        buf.append("id=").append(getId());
        buf.append(",name=").append(getName());
        buf.append(",description=").append(getDescription());
        buf.append('}');
        return buf.toString();
    }
}
